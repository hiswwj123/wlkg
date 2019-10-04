package com.wlkg.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.common.pojo.PageResult;
import com.wlkg.item.pojo.*;
import com.wlkg.mapper.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     * @param id
     * @param type
     */
    private void sendMessage(Long id,String type){
        try {
            amqpTemplate.convertAndSend("item."+type,id);
        } catch (AmqpException e) {
            System.out.println(type+"{}商品消息发送异常，商品 "+id+"：{}");
        }
    }

    public PageResult<Spu> querySpuByPageAndSort(Integer page, Integer
            rows, Boolean saleable, String key) {
        // 1、查询 SPU
        // 分页,最多允许查 100 条
        PageHelper.startPage(page, Math.min(rows, 100));
        // 创建查询条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // 是否过滤上下架
        if (saleable != null) {
            criteria.orEqualTo("saleable", saleable);
        }
        // 是否模糊查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        criteria.andEqualTo("valid", true);
        //默认排序
        example.setOrderByClause("last_update_time desc");
        //查询
        List<Spu> spus = this.spuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(spus)) {
            //throw new WlkgException(ExceptionEnums.GOODS_NOT_FOUND);
        }
        PageInfo<Spu> pageInfo = new PageInfo<Spu>(spus);
        for (Spu spu : spus) {
            // 2、查询 spu 的商品分类名称,要查三级分类
            List<String> names = this.categoryService.queryNameByIds(
                    Arrays.asList(spu.getCid1(), spu.getCid2(),
                            spu.getCid3()));
            // 将分类名称拼接后存入
            spu.setCname(StringUtils.join(names, "/"));
            // 3、查询 spu 的品牌名称
            Brand brand =
                    this.brandMapper.selectByPrimaryKey(spu.getBrandId());
            spu.setBname(brand.getName());
        }
        return new PageResult<>(pageInfo.getTotal(), spus);
    }

    public SpuDetail querySpuDetailById(Long id) {
        return this.spuDetailMapper.selectByPrimaryKey(id);
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        // 查询 sku
        Sku record = new Sku();
        record.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(record);
        for (Sku sku : skus) {
            // 同时查询出库存
            sku.setStock(this.stockMapper.selectByPrimaryKey(sku.getId()).getStock())
            ;
        }
        return skus;
    }

    /**
     * 保存商品信息
     *
     * @param spu
     */
    @Transactional
    public void save(Spu spu) {
        // 1.添加spu
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(new Date());
        spuMapper.insert(spu);

        // 2.添加spu_detail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);
        // 3.添加sku和stock
        saveSkuAndStock(spu);
        //发送消息指定routingkey
        this.sendMessage(spu.getId(),"insert");
    }

    /**
     * 保存sku和stock
     *
     * @param spu
     */
    private void saveSkuAndStock(Spu spu) {
        List<Stock> stocks = new ArrayList<>();
        // 3.循环添加sku
        for (Sku sku : spu.getSkus()) {
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(new Date());
            skuMapper.insert(sku);

            Stock s = new Stock();
            s.setSkuId(sku.getId());
            s.setStock(sku.getStock());
            stocks.add(s);
        }
        // 4.添加库存信息
        stockMapper.insertList(stocks);
    }

    /**
     * 修改商品
     * 修改需要判断sku的一些属性，很难判断，所以干脆直接删除当前spuId对应的sku所有信息
     * 对应的要删除掉stock数据
     * 接着插入数据
     *
     * @param spu
     */
    public void update(Spu spu) {
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skus = skuMapper.select(sku);

        if (!CollectionUtils.isEmpty(skus)) {
            //删除sku
            skuMapper.delete(sku);
            //获取所有的skuId
            List<Long> skuIds = skus.stream().map(Sku::getId).collect(Collectors.toList());
            //根据skuId删除所有对应的skuId
            stockMapper.deleteByIdList(skuIds);
        }
        //修改spu,这些字段不想重新修改
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);

        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1) {
            throw new WlkgException(ExceptionEnums.SPU_UPDATE_FAIL);
        }
        this.spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        //重新插入数据
        saveSkuAndStock(spu);

        //发送消息指定routingkey
        this.sendMessage(spu.getId(),"update");
    }


    @Transactional
    public void deleteGoods(Long spuId) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setLastUpdateTime(new Date());
        spu.setValid(false);
        spuMapper.updateByPrimaryKeySelective(spu);

        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId", spuId);
        Sku sku = new Sku();
        sku.setEnable(false);
        skuMapper.updateByExampleSelective(sku, example);
        this.sendMessage(spuId,"delete");
    }

    /**
     * 下架商品
     * @param id
     * @param saleable
     */
    public void updateSaleable(Long id, Boolean saleable) {
        Spu spu = new Spu();
        spu.setId(id);
        Boolean sale = !saleable;
        System.out.println(sale);
        spu.setSaleable(sale);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 删除时候更新字段
     * @param id
     */
    @Transactional
    public void updateValidById(Long id) {
        //修改spu的valid字段
        Spu spu = new Spu();
        spu.setId(id);
        spu.setValid(false);
        spuMapper.updateByPrimaryKeySelective(spu);

        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skus = skuMapper.select(sku);

        //循环修改sku的Enable
        for (Sku oldSku : skus) {
            oldSku.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(oldSku);
        }
    }

    /**
     * 修改saleable字段
     * @param id
     */
    public void updateSaleAbleById(Long id) {
        Spu spu = new Spu();
        spu.setId(id);
        spu.setSaleable(false);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 上架商品
     * @param id
     */
    public void putAway(Long id) {
        Spu spu = new Spu();
        spu.setId(id);
        spu.setSaleable(true);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 根据spuId查询spu信息
     *
     * @param spuId
     * @return
     */
    public Spu querySpuById(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (spu == null) {
            throw new WlkgException(ExceptionEnums.SPU_IS_NULL);
        }
        //查询出所有的skus
        spu.setSkus(querySkuBySpuId(spuId));
        //查询出spu的详情
        spu.setSpuDetail(querySpuDetailById(spuId));

        return spu;
    }
}