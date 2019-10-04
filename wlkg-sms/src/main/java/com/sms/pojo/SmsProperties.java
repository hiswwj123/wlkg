package com.sms.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/27 0027 16:15
 */
@Data
@ConfigurationProperties(prefix = "wlkg.sms")
public class SmsProperties {
    private String accessKeyId; //accessKeyId
    private String accessKeySecret; //accessKeySecret
    private String signName; //标签名
    private String verifyCodeTemplate; //模板名称
}
