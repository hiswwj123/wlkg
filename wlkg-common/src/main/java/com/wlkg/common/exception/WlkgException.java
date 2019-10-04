package com.wlkg.common.exception;

import com.wlkg.common.enums.ExceptionEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author:wangjun
 * @Data:Createa in 2019/9/6 0006 17:31
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WlkgException extends RuntimeException{
    private ExceptionEnums exceptionEnums;
}
