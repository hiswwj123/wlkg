package com.wlkg.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/24 0024 19:45
 */

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class WlkgGoodsPage {
    public static void main(String[] args) {
        SpringApplication.run(WlkgGoodsPage.class,args);
    }
}
