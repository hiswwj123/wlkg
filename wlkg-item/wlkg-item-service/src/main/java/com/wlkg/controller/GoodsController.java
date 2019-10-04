package com.wlkg.controller;

import com.wlkg.common.pojo.PageResult;
import com.wlkg.item.pojo.Sku;
import com.wlkg.item.pojo.Spu;
import com.wlkg.item.pojo.SpuDetail;
import com.wlkg.service.GoodsService;
import org.bouncycastle.asn1.esf.SPuri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer
                    page,
            @RequestParam(value = "rows", defaultValue = "5") Integer
                    rows,
            @RequestParam(value = "saleable", required = false) Boolean
                    saleable,
            @RequestParam(value = "key", required = false) String key) {
        // 分页查询 spu 信息
        PageResult<Spu> result =
                this.goodsService.querySpuByPageAndSort(page, rows, saleable, key);
        return ResponseEntity.ok(result);
    }

    /*@GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetail(@PathVariable("id")Long id){
        return ResponseEntity.ok(goodsService.querySpuDetail(id));
    }*/

    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable("id") Long id) {
        SpuDetail detail = this.goodsService.querySpuDetailById(id);
        return ResponseEntity.ok(detail);
    }

    @PutMapping("/sku/valid/{id}")
    public ResponseEntity<Void> updateValidById(@PathVariable("id") Long id){
        goodsService.updateValidById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    /*@GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySku(Long id){
        //System.out.println(id);
        return ResponseEntity.ok(goodsService.querySku(id));
    }*/
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id) {
        List<Sku> skus = this.goodsService.querySkuBySpuId(id);
        return ResponseEntity.ok(skus);
    }



    @PostMapping("/goods")
    public ResponseEntity<Void> addSpu(@RequestBody Spu spu){
        try {
            this.goodsService.save(spu);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/goods")
    public ResponseEntity<Void> updateSpu(@RequestBody Spu spu){
        this.goodsService.update(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/spu/delete")
    public ResponseEntity<Void> deleteGoods(Long id){
        goodsService.deleteGoods(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/spu/update")
    public ResponseEntity<Void> updateSaleable(@RequestParam("id")Long id,@RequestParam("saleable") Boolean saleable){

        goodsService.updateSaleable(id,saleable);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据spuId查询spu信息
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        Spu spu = this.goodsService.querySpuById(id);
        return ResponseEntity.ok(spu);
    }


    /**
     * 商品下架
     * @param id
     * @return
     */
    @PutMapping("/sku/saleable/{id}")
    public ResponseEntity<Void> updateSaleAbleById(@PathVariable("id") Long id){
        goodsService.updateSaleAbleById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 商品上架
     * @param id
     * @return
     */
    @PutMapping("/sku/putAway/{id}")
    public ResponseEntity<Void> putAway(@PathVariable("id") Long id){
        goodsService.putAway(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}