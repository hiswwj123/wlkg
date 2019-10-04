package com.wlkg.goods.client;

import com.wlkg.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {
}