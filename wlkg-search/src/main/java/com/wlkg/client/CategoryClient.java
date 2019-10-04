package com.wlkg.client;

import com.wlkg.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 17:08
 * @Description:
 */

@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
