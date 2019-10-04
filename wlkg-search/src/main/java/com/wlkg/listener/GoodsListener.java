package com.wlkg.listener;

import com.wlkg.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/26 0026 12:09
 */
@Component
public class GoodsListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "wlkg.create.index.queue", durable =
                    "true"),
            exchange = @Exchange(
                    value = "wlkg.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}))
    public void listenCreate(Long id) throws Exception {
        if (id == null) {
            return;
        }
        // 创建或更新索引
        this.searchService.createIndex(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "wlkg.create.index.queue", durable =
                    "true"),
            exchange = @Exchange(
                    value = "wlkg.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.delete"}))
    public void listenCreate2(Long id) throws Exception {
        if (id == null) {
            return;
        }
        // 创建或更新索引
        this.searchService.deleteIndex(id);
    }

}
