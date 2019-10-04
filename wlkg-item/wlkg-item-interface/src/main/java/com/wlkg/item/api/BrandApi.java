package com.wlkg.item.api;

import com.wlkg.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 11:36
 * @Description:
 */

public interface BrandApi {
    @GetMapping("/brand/{id}")
    Brand queryBrandById(@PathVariable("id") Long id) ;
}
