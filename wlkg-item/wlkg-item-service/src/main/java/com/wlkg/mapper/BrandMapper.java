package com.wlkg.mapper;

import com.wlkg.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Delete("delete from tb_category_brand where brand_id = #{id}")
    public int deleteBrand(@Param("id") Long id);

    @Select("select category_id from tb_category_brand where brand_id = #{id}")
    public List<Long> selectCategoryId(@Param("id") Long id);

    @Select("SELECT b.* FROM tb_brand b LEFT JOIN tb_category_brand cb ON b.id = cb.brand_id WHERE cb.category_id = #{cid}")
    public List<Brand>queryByCategoryId(Long cid);

    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid")Long cid, @Param("bid")Long id);

    @Delete("delete from tb_category_brand where brand_id = #{id}")
    int deleteCategoryIdByBid(@Param("id")Long id);

    @Select("select tb_brand.* from tb_brand LEFT JOIN tb_category_brand on tb_brand.id = tb_category_brand.brand_id where tb_category_brand.category_id = #{cid}")
    List<Brand> queryBrandByCid(Long cid);

}