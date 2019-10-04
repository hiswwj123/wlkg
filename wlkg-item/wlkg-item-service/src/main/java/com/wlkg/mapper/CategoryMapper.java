package com.wlkg.mapper;

import com.wlkg.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/6 0006 23:34
 */
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category, Long> {

    @Select("select category_id from tb_category_brand where brand_id = #{brand_id}")
    Long[] queryCategory(@Param("brand_id") Long brand_id);
}
