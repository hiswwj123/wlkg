package com.wlkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.wlkg.mapper")
public class WlkgItemService {
    public static void main(String[] args) {
        SpringApplication.run(WlkgItemService.class,args);
    }
}