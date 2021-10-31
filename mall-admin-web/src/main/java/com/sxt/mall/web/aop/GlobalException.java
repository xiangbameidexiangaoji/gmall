package com.sxt.mall.web.aop;

import com.sxt.mall.to.CommonResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 统一异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(value = BindException.class)
    public Object handleBindException(BindException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        log.error("[系统全局异常感知,信息:{}]", fieldErrors);
        return new CommonResult().failed();
    }

    @ExceptionHandler(value = Exception.class)
    public Object handException(Exception e) {
        log.error("[系统全局异常感知,信息:{}]", e.getStackTrace());
        return new CommonResult().failed();
    }

}
