package com.sms.com.listener;

import com.aliyuncs.CommonResponse;
import com.sms.pojo.SmsProperties;
import com.sms.utils.SmsUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/27 0027 19:34
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "sms.verify.code.query",durable = "true"),
            exchange = @Exchange(value = "wlkg.sms.exchange",type= ExchangeTypes.TOPIC),
            ignoreDeclarationExceptions = "true",
            key = {"sms.verify.code"}))
    public void listenSms(Map<String, String> msg){
        System.out.println(msg);
        if(CollectionUtils.isEmpty(msg)){
            //放弃处理
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            return;
        }
        //发送消息
        CommonResponse commonResponse = smsUtils.sendSms(phone, code);
    }

}
