package com.wlkg.controller;

import com.wlkg.item.pojo.Category;
import com.wlkg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/6 0006 23:28
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryByPid(@RequestParam("pid")Long pid){
        List<Category> list = categoryService.queryCategoryByPid(pid);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Integer> addCategory(@RequestBody Category node){
        int i = categoryService.addCategory(node);
        return ResponseEntity.ok(i);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<Integer> updateCategory(@RequestParam("id") Long id, @RequestParam("name") String name) {
        int i = categoryService.updateCategory(id, name);
        return ResponseEntity.ok(i);
    }

    /**
     * 编辑商品
     * @param id
     * @return
     */
    @GetMapping("/queryCategory")
    public ResponseEntity<List<Category>> queryCategoryByBid(@RequestParam("id") Long id) {
        List<Category> categoryList = categoryService.queryCategoryByBid(id);
        return ResponseEntity.ok(categoryList);
    }

    @RequestMapping("/deleteCategory")
    public ResponseEntity<Integer> deleteCategory(@RequestParam("id") Long id) {
        int i = categoryService.deleteCategory(id);
        return ResponseEntity.ok(i);
    }

    /**
     * 根据Id查询商品分类
     * @param ids
     * @return
     */
    @GetMapping("list/ids")
    public ResponseEntity<List<Category>>
    queryCategoryByIds(@RequestParam("pid")List<Long> ids){
        return ResponseEntity.ok(categoryService.queryByIds(ids));
    }

}
