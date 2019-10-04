package com.wlkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/8/31 0031 14:19
 */
@SpringBootApplication
@EnableEurekaServer
public class WlkgRegistry {
    public static void main(String[] args) {
        SpringApplication.run(WlkgRegistry.class,args);
    }
}
