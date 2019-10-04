package com.wlkg.controller;

import com.wlkg.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/10 0010 14:03
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @RequestMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
        String url = uploadService.uploadImage(file);
        if(StringUtils.isBlank(url)){
            //url为空就证明文件上传失败
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        // 返回 200，并且携带 url 路径
        return ResponseEntity.ok(url);

    }
}
