package com.ruoyisecurity.handler;

//...

import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局系统异常处理器
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public Result<String> handleServiceException(ServiceException ex) {
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleServiceException(RuntimeException ex) {
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleServiceException(Exception ex) {
        return Result.error(ex.getMessage());
    }

}
