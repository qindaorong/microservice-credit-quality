package com.galaxy.microservice.sms.controller;

import com.galaxy.framework.bean.dto.RequestDto;
import com.galaxy.framework.redis.components.GalaxyRedisTemplate;
import com.galaxy.framework.verify.VerifyManage;
import com.galaxy.framework.web.constants.ClientConstant;
import com.galaxy.microservice.sms.bean.dto.MessageDto;
import com.galaxy.microservice.sms.common.components.SpringApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName： BaseController
 * @Description
 * @Author alan qin
 * @Date 2019-04-08
 **/
@Slf4j
public abstract class BaseController extends VerifyManage {

    GalaxyRedisTemplate redisTemplate;

    public void init(){
        redisTemplate = SpringApplicationContext.getBean(GalaxyRedisTemplate.class);
    }

    private String loadClientPublicKey(HttpServletRequest request){
        String clientId = request.getHeader(ClientConstant.CLIENT_ID);
        String publicKeyStr = String.valueOf(redisTemplate.getHashKey(clientId, ClientConstant.PUBLIC_KEY));
        log.info("client: [{}] ,publicKey is [{}]",clientId,publicKeyStr);
        return publicKeyStr;
    }

    public MessageDto loadDto(HttpServletRequest request, RequestDto dto, Class<MessageDto> clazz){
        String clientPublicKey =this.loadClientPublicKey(request);
        return super.loadRequirementDto(clientPublicKey,dto,clazz);
    }
}
