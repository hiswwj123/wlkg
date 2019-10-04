package com.wlkg.test;

import com.sms.WlkgSmSApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/27 0027 19:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WlkgSmSApplication.class)
public class SmsTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendTest(){
        Map<String,String> map = new HashMap<>();
        map.put("phone","13755253848");
        map.put("code","0523");

        amqpTemplate.convertAndSend("wlkg.sms.exchange","sms.verify.code",map);

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
