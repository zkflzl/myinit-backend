package com.zkflzl.myinit.config;

import com.zkflzl.myinit.common.ErrorCode;
import com.zkflzl.myinit.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author <a href="https://github.com/zkflzl">程序员zk</a>
 **/

@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResultUtils handleRuntimeException(RuntimeException e) {
        log.error(e.toString(), e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"服务器异常" );
    }
}
