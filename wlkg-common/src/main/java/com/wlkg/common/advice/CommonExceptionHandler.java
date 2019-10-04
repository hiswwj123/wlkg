package com.wlkg.common.advice;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.ExceptionResult;
import com.wlkg.common.exception.WlkgException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/6 0006 16:44
 */
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResult> handleException(WlkgException e){
        ExceptionEnums em = e.getExceptionEnums();
        return ResponseEntity.ok(new ExceptionResult(em));
    }

}
