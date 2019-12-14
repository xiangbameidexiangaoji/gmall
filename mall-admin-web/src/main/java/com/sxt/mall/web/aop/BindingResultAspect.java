package com.sxt.mall.web.aop;

import com.sxt.mall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Slf4j
@Component
public class BindingResultAspect {

    @Pointcut("execution(* com.sxt.mall.web..*Controller.*(..))")
    public void BindingResult(){};

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();//获取所有参数到数组中
        for(Object obj : args){
            if(obj instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) obj;
                if(bindingResult.getErrorCount() > 0){
                    return new CommonResult().validateFailed(bindingResult);
                }
            }
        }
        Object proceed = joinPoint.proceed();
//        Arrays.asList(args)
//                .forEach((obj)->{
//                    if(obj instanceof BindingResult){
//                        BindingResult bindingResult = (BindingResult) obj;
//                        if(bindingResult.getErrorCount() > 0){
//                            new CommonResult().validateFailed(bindingResult);
//                        }
//                    }
//                });
        return proceed;
    }
}


