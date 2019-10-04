package com.wlkg.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/10 0010 14:05
 */
@Service
public class UploadService {

    @Autowired
    FastFileStorageClient storageClient;

    //支持文件的类型
    private static final List<String> suffixes =
            Arrays.asList("image/png", "image/jpeg");
    /*public String uploadImage(MultipartFile file){
        //检验文件的类型
        String type = file.getContentType();
        if(!suffixes.contains(type)){
            throw new WlkgException(ExceptionEnums.FILE_UPLOAD_TYPR_ERROR);
        }

        try {
            //检验文件上传的内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                throw new WlkgException(ExceptionEnums.FILE_UPLOAD_INFO_NOTNULL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.保存图片
        File dir = new File("G:\\tem\\upload");
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(dir, file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = "http://image.wlkg.com/upload/" +
                file.getOriginalFilename();
        return url;
    }*/

     public String uploadImage(MultipartFile file){
        //检验文件的类型
        String type = file.getContentType();
        if(!suffixes.contains(type)){
            throw new WlkgException(ExceptionEnums.FILE_UPLOAD_TYPR_ERROR);
        }

        try {
            //检验文件上传的内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                throw new WlkgException(ExceptionEnums.FILE_UPLOAD_INFO_NOTNULL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将图片上传到FastDFS
        //2.1.获取文件后缀名
        String extension =
                StringUtils.substringAfterLast(file.getOriginalFilename(),".");

        //2.2、上传
         try {
             StorePath storePath = this.storageClient.uploadFile(
                     file.getInputStream(),file.getSize(),extension,null
             );
             //2.3、返回完整的路径
             return "http://image.wlkg.com/" + storePath.getFullPath();
         } catch (IOException e) {
             return null;
         }

    }

}
