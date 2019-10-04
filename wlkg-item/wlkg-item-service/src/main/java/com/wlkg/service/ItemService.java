package com.wlkg.service;

import com.wlkg.item.pojo.Item;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/6 0006 14:35
 */
@Service
public class ItemService {
    public Item saveItem(Item item){
        int id = new Random().nextInt(100);
        item.setId(id);
        return item;
    }
}
