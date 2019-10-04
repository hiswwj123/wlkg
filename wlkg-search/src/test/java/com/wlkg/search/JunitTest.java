package com.wlkg.search;

import com.wlkg.WlkgSearchService;
import com.wlkg.client.BrandClient;
import com.wlkg.client.CategoryClient;
import com.wlkg.item.pojo.Brand;
import com.wlkg.item.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 17:36
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WlkgSearchService.class)
public class JunitTest {

    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private BrandClient brandClient;


    @Test
    public void test01(){
        List<Category> categoryList = categoryClient.queryCategoryByIds(Arrays.asList(1L, 2L, 3L));
        System.out.println(categoryList);
    }

    @Test
    public void test02(){
        Brand brand = brandClient.queryBrandById(8740L);
        System.out.println(brand);
    }

}
