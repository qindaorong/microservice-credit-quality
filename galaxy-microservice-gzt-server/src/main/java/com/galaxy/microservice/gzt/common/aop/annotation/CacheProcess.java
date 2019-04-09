package com.galaxy.microservice.gzt.common.aop.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface CacheProcess {
    
    //金额检查
    boolean amountSwitch() default true;

    //缓存检查
    boolean cacheSwitch() default true;
}