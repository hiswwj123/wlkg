package com.wlkg.goods.controller;

import com.wlkg.goods.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.attribute.standard.PagesPerMinute;
import java.util.Map;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/24 0024 20:02
 */
@Controller
@RequestMapping("item")
public class GoodsController {

    @Autowired
    private PageService pageService;

    /**
     * 跳转详情页
     * @param id
     * @return
     */
   @GetMapping("{id}.html")
    public String toItemPage(Model model, @PathVariable("id") Long id){
       //查询模型数据
       Map<String,Object> attributes = pageService.loadModel(id);
       //准备模型数据
       model.addAllAttributes(attributes);

       // 判断是否需要生成新的页面
       if(!pageService.exists(id)){
           this.pageService.syncCreateHtml(id);
       }

       return "item";
    }
}
