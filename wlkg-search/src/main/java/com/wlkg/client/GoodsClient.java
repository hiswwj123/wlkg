package com.wlkg.client;

import com.wlkg.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 17:07
 * @Description:
 */

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
