package com.wlkg.test;

import com.wlkg.WlkgSearchService;
import com.wlkg.client.GoodsClient;
import com.wlkg.common.pojo.PageResult;
import com.wlkg.item.pojo.Spu;
import com.wlkg.pojo.Goods;
import com.wlkg.repository.GoodsRepository;
import com.wlkg.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 19:31
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WlkgSearchService.class)
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private SearchService searchService;

    @Test
    public void test01(){
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }

    @Test
    public void loadData(){
        int page = 1;
        int rows = 100;
        int size = 0;
        do {
            // 查询分页数据
            PageResult<Spu> result = this.goodsClient.querySpuByPage(page, rows, true, null);
            List<Spu> spus = result.getItems();
            size = spus.size();
            // 创建 Goods 集合
            List<Goods> goodsList = new ArrayList<>();
            // 遍历 spu
            for (Spu spu : spus) {
                try {
                    Goods goods = this.searchService.buildGoods(spu);
                    goodsList.add(goods);
                } catch (Exception e) {
                    break;
                }
            }
            this.goodsRepository.saveAll(goodsList);
            page++;
        } while (size == 100);
    }
}
