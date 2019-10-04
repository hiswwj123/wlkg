package com.wlkg.mapper;

import com.wlkg.item.pojo.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface SkuMapper extends Mapper<Sku> {

    @Update("update tb_sku set enable = false where spu_id = #{spuId}")
    public int deleteGoodsBySpuId(@Param("spuId")Long id);
}
