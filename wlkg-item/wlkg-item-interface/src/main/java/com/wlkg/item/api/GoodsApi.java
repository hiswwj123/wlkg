package com.wlkg.item.api;

import com.wlkg.common.pojo.PageResult;
import com.wlkg.item.pojo.Sku;
import com.wlkg.item.pojo.Spu;
import com.wlkg.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 11:30
 * @Description:
 */
public interface GoodsApi {

    /**
     * 分页查询商品
     *
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    PageResult<Spu> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key);

    /**
     * 根据 spu 商品 id 查询详情
     * @param id
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    SpuDetail querySpuDetailById(@PathVariable("id") Long id);

    /**
     * 根据 spu 的 id 查询 sku
     * @param id
     * @return
     */
    @GetMapping("/sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long id);

    /**
     * 根据spuId查询spu信息
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
   Spu querySpuById(@PathVariable("id") Long id);

}
