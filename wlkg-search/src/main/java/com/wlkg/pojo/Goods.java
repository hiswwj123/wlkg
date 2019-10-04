package com.wlkg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Mr Chen
 * @Date: 2019/9/23 11:18
 * @Description:
 */

@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;  // 所有需要被搜索的信息，包含标题，分类，甚至品牌

    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;

    private Long brandId;// 品牌 id
    private Long cid1;// 1 级分类 id
    private Long cid2;// 2 级分类 id
    private Long cid3;// 3 级分类 id
    private Date createTime;// 创建时间
    private List<Long> price;// 价格
    @Field(type = FieldType.Keyword, index = false)
    private String skus;// sku 信息的 json 结构
    private Map<String, Object> specs;// 可搜索的规格参数，key 是参数名，值是参数值


}
