package com.wlkg.goods.client;

import com.wlkg.item.api.SpecificationAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface SpecificationClient extends SpecificationAPI {
}