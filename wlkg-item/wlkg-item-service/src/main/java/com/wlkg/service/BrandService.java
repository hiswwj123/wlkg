package com.wlkg.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.common.pojo.PageResult;
import com.wlkg.item.pojo.Brand;
import com.wlkg.item.pojo.Category;
import com.wlkg.mapper.BrandMapper;
import com.wlkg.mapper.CategoryMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public PageResult<Brand> queryBrandByPageAndSort(
            Integer page, Integer rows, String sortBy, Boolean desc,
            String key) {
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            //创建约束条件
            example.createCriteria().andLike("name", "%" + key + "%")
                    .orEqualTo("letter", key);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            System.out.println(orderByClause);
            example.setOrderByClause(orderByClause);
        }
        // 查询
        /*Page<Brand> pageInfo = (Page<Brand>)
                brandMapper.selectByExample(example);*/

        List<Brand> brandList = brandMapper.selectByExample(example);
        if(brandList == null){
            //throw new WlkgException(ExceptionEnums.BRAND_CANNOT_BE_NULL);
        }
        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);

        // 返回结果
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Transactional
    public void addBrand(Brand brand,List<Long> cids){
        //新增品牌信息
        brandMapper.insertSelective(brand);
        //新增品牌和分类中间表
        for(Long cid : cids){
            int row = brandMapper.insertCategoryBrand(cid,brand.getId());
        }
    }

    @Transactional
    public void deleteBrand(Long id){
        brandMapper.deleteByPrimaryKey(id);
        brandMapper.deleteBrand(id);
    }

    @Transactional
    public void updateBrand(Brand brand,List<Long> cids){
        /*Example example = new Example(Brand.class);
        example.createCriteria().orEqualTo(name).orEqualTo(letter).orEqualTo(image);*/
        /*Example example = new Example(Brand.class);
        example.createCriteria().orEqualTo("name",brand.getName()).orEqualTo("letter",brand.getLetter()).orEqualTo("image",brand.getImage());
        System.out.println(example);*/
        int row = brandMapper.deleteBrand(brand.getId());
        System.out.println(row);
        if(row == 0){
            //throw new WlkgException(ExceptionEnums.UPDATE_BRAND_FAILED);
        }
        int row1 = brandMapper.updateByPrimaryKeySelective(brand);
        System.out.println(row1);
        if(row1 == 0){
            //throw new WlkgException(ExceptionEnums.UPDATE_BRAND_FAILED);
        }
        for(Long cid : cids){
            int row2 = brandMapper.insertCategoryBrand(cid,brand.getId());
            System.out.println(row2);
            if(row2 == 0){
                //throw new WlkgException(ExceptionEnums.UPDATE_BRAND_FAILED);
            }
        }
        //brandCa
    }

    public List<Category> selectBrand(Long id){
        List<Long> list =  brandMapper.selectCategoryId(id);
        List<Category> categoryList = new ArrayList<>();
        for(Long l : list){
            Category category = categoryMapper.selectByPrimaryKey(l);
            categoryList.add(category);
        }
        return categoryList;
    }

    public List<Brand> queryBrandByCategory(Long cid) {
        return this.brandMapper.queryByCategoryId(cid);
    }


    @Transactional
    public void save(Brand brand, List<Long> cids) {
        // 新增品牌信息
        brandMapper.insertSelective(brand);
        // 新增品牌和分类中间表
        for (Long cid : cids) {
            brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    public void deleteById(Long id){
        Brand brand = new Brand();
        brand.setId(id);
        //删除品牌数据
        brandMapper.delete(brand);
        //删除商品的品牌对应类别关系
        brandMapper.deleteCategoryIdByBid(brand.getId());
    }

    @Transactional
    public void update(Brand brand, List<Long> cids){
        //修改品牌信息
        brandMapper.updateByPrimaryKeySelective(brand);
        //删除品牌信息
        int i = brandMapper.deleteCategoryIdByBid(brand.getId());
        // 新增品牌和分类中间表
        for (Long cid : cids){
            brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    public List<Brand> queryBrandByCid(Long cid){
        List<Brand> brands = brandMapper.queryBrandByCid(cid);
        if(CollectionUtils.isEmpty(brands)){
            throw new WlkgException(ExceptionEnums.BRAND_IS_EMPTY);
        }
        return brands;
    }

    public Brand queryById(Long id) {
        return this.brandMapper.selectByPrimaryKey(id);
    }
}