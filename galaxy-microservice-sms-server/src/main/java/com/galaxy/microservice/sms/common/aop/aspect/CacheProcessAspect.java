package com.galaxy.microservice.sms.common.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.framework.exception.BusinessException;
import com.galaxy.framework.redis.components.GalaxyRedisTemplate;
import com.galaxy.microservice.sms.bean.dto.MessageDto;
import com.galaxy.microservice.sms.common.exception.ExceptionCode;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @ClassName： CacheProcessAspect
 * @Description
 * @Author alan qin
 * @Date 2019-04-09
 **/
@Aspect
@Slf4j
public class CacheProcessAspect {

    @Autowired
    GalaxyRedisTemplate redisTemplate;

    @Autowired
    Environment environment;

    @Pointcut("args(com.galaxy.microservice.sms.bean.dto.MessageDto) @annotation(com.galaxy.microservice.sms.common.aop.annotation.MethodCheck)")
    public void pointCut(){}

    /** *
     *@描述   前置查询
     *@参数  [joinPoint]
     *@返回值  void
     *@创建人  alan qin
     *@创建时间  04/09/2019
     *@修改人和其它信息
     **/
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        if(!CollectionUtils.isEmpty(Lists.newArrayList(joinPoint.getArgs()))){
            MessageDto messageDto  = (MessageDto)joinPoint.getArgs()[0];
            Boolean isOpenChannel= this.checkMethodChannelAuth(messageDto,methodName);
            if(!isOpenChannel){
                throw new BusinessException(ExceptionCode.CHANNEL_NO_OPEN);
            }
        }
    }



    private Boolean checkMethodChannelAuth(MessageDto dto,String methodName){
        Map<String,Object> map = null;
        try {
            String microServiceName =environment.getProperty("spring.application.name");
            Object object = redisTemplate.getHashKey(dto.getClientId(),microServiceName);
            JSONObject jsonObject = JSON.parseObject(object.toString());
            map = JSONObject.toJavaObject(jsonObject, Map.class);
            return map.containsKey(methodName);
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

}
