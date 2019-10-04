package com.wlkg.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/28 0028 14:36
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.wlkg.user.mapper")
public class WlkgUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WlkgUserServiceApplication.class,args);
    }
}
