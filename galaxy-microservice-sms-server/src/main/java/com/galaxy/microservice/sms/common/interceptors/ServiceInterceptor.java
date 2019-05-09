package com.galaxy.microservice.sms.common.interceptors;

import com.galaxy.framework.redis.GalaxyRedisTemplate;
import com.galaxy.framework.util.VerifyManage;
import com.galaxy.framework.web.constants.ClientConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName： ServiceInterceptor
 * @Description
 * @Author alan qin
 * @Date 2019-04-10
 **/
@Component
@Slf4j
public class ServiceInterceptor  extends VerifyManage implements HandlerInterceptor {

    @Autowired
    GalaxyRedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String clientId=request.getHeader(ClientConstant.CLIENT_ID);
            return redisTemplate.hashKey(clientId);
    }

}
