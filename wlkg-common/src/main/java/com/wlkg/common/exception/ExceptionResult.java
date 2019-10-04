package com.wlkg.common.exception;

import com.wlkg.common.enums.ExceptionEnums;
import lombok.Data;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/6 0006 17:36
 */
@Data
public class ExceptionResult {
    private int status; //错误码
    private String message; //错误信息
    private Long timestamp; //时间戳

    public ExceptionResult(ExceptionEnums exceptionEnums){
        this.status = exceptionEnums.getCode();
        this.message = exceptionEnums.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
