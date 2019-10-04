package com.wlkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/8/31 0031 15:03
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class WlkgApiGateWay {
    public static void main(String[] args) {
        SpringApplication.run(WlkgApiGateWay.class,args);
    }
}

