package com.wlkg.client;

import com.wlkg.item.api.SpecificationAPI;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: Mr Cheng
 * @Date: 2019/9/23 17:09
 * @Description:
 */

@FeignClient("item-service")
public interface SpecificationClient extends SpecificationAPI {
}
