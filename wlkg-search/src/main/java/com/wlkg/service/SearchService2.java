/*
package com.wlkg.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wlkg.client.BrandClient;
import com.wlkg.client.CategoryClient;
import com.wlkg.client.GoodsClient;
import com.wlkg.client.SpecificationClient;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.item.pojo.*;
import com.wlkg.pojo.Goods;
import com.wlkg.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

*/
/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 20:01
 * @Description:
 *//*


@Service
public class SearchService2 {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specClient;

    */
/*public Goods buildGoods(Spu spu) {

        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        List<String> names = categories.stream().map(s -> s.getName()).collect(Collectors.toList());

        Brand brand = brandClient.queryBrandById(spu.getBrandId());

        String all = spu.getTitle() + StringUtils.join(names, " ") + brand.getName();

        List<Sku> skus = goodsClient.querySkuBySpuId(spu.getId());

        List<Long> prices = new ArrayList<>();

        List<Map<String, Object>> skuList = new ArrayList<>();

        skus.forEach(sku -> {
            prices.add(sku.getPrice());
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", sku.getId());
            skuMap.put("title", sku.getTitle());
            skuMap.put("price", sku.getPrice());
            //skuMap.put("image", StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            skuMap.put("image", StringUtils.substringBefore(sku.getImages(), ","));
            skuList.add(skuMap);
        });

        List<SpecParam> params = specificationClient.querySpecParam(null, spu.getCid3(), true, null);

        SpuDetail spuDetail = goodsClient.querySpuDetailById(spu.getId());
        //获取通用规格
        Map<Long, String> genericSpec = JsonUtils.parseMap(spuDetail.getGenericSpec(), Long.class, String.class);
        //获取特有规格属性
        Map<Long, List<String>> specialSpec =
                JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {});
        //定义 spec 对应的 map
        HashMap<String, Object> specs = new HashMap<>();
        //对规格进行遍历，并封装 spec，其中 spec 的 key 是规格参数的名称，值是商品详情中的值
        for (SpecParam param : params) {
            //key 是规格参数的名称
            String key = param.getName();
            Object value = "";
            if (param.getGeneric()) {
                //参数是通用属性，通过规格参数的 ID 从商品详情存储的规格参数中查出值
                value = genericSpec.get(param.getId());
                if (param.getNumeric()) {
                    //参数是数值类型，处理成段，方便后期对数值类型进行范围过滤
                    value = chooseSegment(value.toString(), param);
                }
            } else {
                //参数不是通用类型
                value = specialSpec.get(param.getId());
            }
            value = (value == null ? "其他" : value);
            //存入 map
            specs.put(key, value);
        }

        Goods goods = new Goods(spu.getId(),all,spu.getSubTitle(),spu.getBrandId(),spu.getCid1(),spu.getCid2(),
                spu.getCid3(),spu.getCreateTime(),prices,JsonUtils.serialize(skuList),specs);
        return goods;
    }*//*


    public Goods buildGoods(Spu spu) {
        //查询分类
        List<Category> categories =
                categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(),
                        spu.getCid2(), spu.getCid3()));

        List<String> names =
                categories.stream().map(Category::getName).collect(Collectors.toList());
        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());

        //搜素字段
        String all = spu.getTitle() + StringUtils.join(names, " ")+brand.getName();
        //查询 sku
        List<Sku> skus = goodsClient.querySkuBySpuId(spu.getId());

        //Set<Long> priceSet =
        skus.stream().map(Sku::getPrice).collect(Collectors.toSet());
        // 处理 sku，仅封装 id、价格、标题、图片，并获得价格集合
        List<Long> prices = new ArrayList<>();
        List<Map<String, Object>> skuList = new ArrayList<>();
        skus.forEach(sku -> {
            prices.add(sku.getPrice());
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", sku.getId());
            skuMap.put("title", sku.getTitle());
            skuMap.put("price", sku.getPrice());
            //skuMap.put("image", StringUtils.isBlank(sku.getImages()) ? "" :StringUtils.split(sku.getImages(), ",")[0]);
            skuMap.put("image", StringUtils.substringBefore(sku.getImages(),
                    ","));
            skuList.add(skuMap);
        });
        //查询规格参数
        List<SpecParam> params = specClient.querySpecParam(null,
                spu.getCid3(), true, null);
        //查询商品详情
        SpuDetail spuDetail = goodsClient.querySpuDetailById(spu.getId());
        //获取通用规格参数
        Map<Long, String> genericSpec =
                JsonUtils.parseMap(spuDetail.getGenericSpec(), Long.class, String.class);
        //获取特有规格参数
        Map<Long, List<String>> specialSpec =
                JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new
                        TypeReference<Map<Long, List<String>>>() {
                        });
        //定义 spec 对应的 map
        HashMap<String, Object> specs = new HashMap<>();
        //对规格进行遍历，并封装 spec，其中 spec 的 key 是规格参数的名称，值是商品详情中的值
        for (SpecParam param : params) {
            //key 是规格参数的名称
            String key = param.getName();
            Object value = "";
            if (param.getGeneric()) {
                //参数是通用属性，通过规格参数的 ID 从商品详情存储的规格参数中查出值
                value = genericSpec.get(param.getId());
                if (param.getNumeric()) {
                    //参数是数值类型，处理成段，方便后期对数值类型进行范围过滤
                    value = chooseSegment(value.toString(), param);
                }
            } else {
                //参数不是通用类型
                value = specialSpec.get(param.getId());
            }
            value = (value == null ? "其他" : value);
            //存入 map
            specs.put(key, value);
        }
        Goods goods = new Goods();
        goods.setId(spu.getId());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        goods.setAll(all);//
        goods.setPrice(prices);// 设置价格
        goods.setSkus(JsonUtils.serialize(skuList));//设置 sku
        goods.setSpecs(specs);//设置规格
        return goods;
    }


    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }
}
*/
