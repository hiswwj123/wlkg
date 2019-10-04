package com.wlkg.controller;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.item.pojo.Item;
import com.wlkg.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/6 0006 14:38
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> saveItem(Item item){
        if(item.getPrice() == null){
          throw new WlkgException(ExceptionEnums.PRICE_CANNOT_BE_NULL);
//          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        itemService.saveItem(item);
        return  ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
