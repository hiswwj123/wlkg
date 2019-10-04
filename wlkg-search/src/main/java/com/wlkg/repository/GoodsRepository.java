package com.wlkg.repository;

import com.wlkg.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 19:28
 * @Description:
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
