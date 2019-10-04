package com.wlkg.item.api;

import com.wlkg.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 11:22
 * @Description:
 */
@RequestMapping("/category")
public interface CategoryApi {

    @GetMapping("list/ids")
    List<Category> queryCategoryByIds(@RequestParam("pid") List<Long> ids);
}
