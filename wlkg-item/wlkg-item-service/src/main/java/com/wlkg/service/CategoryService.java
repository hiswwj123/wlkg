package com.wlkg.service;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.item.pojo.Category;
import com.wlkg.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/6 0006 23:31
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categoryList = categoryMapper.select(category);
        if (CollectionUtils.isEmpty(categoryList)) {
            throw new WlkgException(ExceptionEnums.CATEGOPY_NOT_FOUND);
        }
        return categoryList;
    }

    public List<Category> queryCategoryByBid(Long id) {
        Category category = new Category();
        category.setParentId(id);
        Long[] cids = categoryMapper.queryCategory(id);
        List<Category> categoryList = new ArrayList<>();
        for (Long categoryid:cids) {
            Category category1 = categoryMapper.selectByPrimaryKey(categoryid);
            categoryList.add(category1);
        }
        return categoryList;
    }

    public int addCategory(Category category) {
        int addCount = categoryMapper.insert(category);
        return addCount;
    }

    public int updateCategory(Long id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        //int i = categoryMapper.updateByExample(category, name);
        int i = categoryMapper.updateByPrimaryKeySelective(category);
        return i;
    }

    public int deleteCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        int i = categoryMapper.deleteByPrimaryKey(category);
        return i;
    }

    public List<String> queryNameByIds(List<Long> ids) {
        List<String> stringList = categoryMapper.selectByIdList(ids).stream().map(Category::getName).collect(Collectors.toList());
        //System.out.println(stringList);
        return stringList;
    }

    public List<Category> queryByIds(List<Long> ids) {
        List<Category> list = this.categoryMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)) {
            //throw new WlkgException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }
}
