package com.wlkg.controller;

import com.wlkg.common.pojo.PageResult;
import com.wlkg.item.pojo.Brand;
import com.wlkg.item.pojo.Category;
import com.wlkg.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) {
        PageResult<Brand> result =
                brandService.queryBrandByPageAndSort(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    /**
     * 添加品牌
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void>save(Brand brand, @RequestParam("cids")
            List<Long> cids) {
        //添加商品
        brandService.addBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteById(@RequestParam("id") Long id){
        brandService.deleteById(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/editById")
    public ResponseEntity<Void> editById(Brand brand, @RequestParam("cids")
            List<Long> cids){
        brandService.update(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid){
        List<Brand> brands = brandService.queryBrandByCid(cid);
        return ResponseEntity.ok(brands);
    }

    @GetMapping("selectBrand")
    public ResponseEntity<List<Category>> selectBrand(Long id){
        List<Category> categoryList = brandService.selectBrand(id);
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok(brandService.queryById(id));
    }

}