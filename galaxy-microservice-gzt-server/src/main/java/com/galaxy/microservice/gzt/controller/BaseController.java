package com.galaxy.microservice.gzt.controller;

import com.galaxy.framework.bean.dto.RequestDto;
import com.galaxy.framework.redis.components.GalaxyRedisTemplate;
import com.galaxy.framework.verify.VerifyManage;
import com.galaxy.microservice.gzt.bean.dto.CreditQualityDto;
import com.galaxy.microservice.gzt.common.components.SpringApplicationContext;
import com.galaxy.microservice.gzt.common.constants.ClientConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName： BaseController
 * @Description
 * @Author alan qin
 * @Date 2019-04-08
 **/
public abstract class BaseController  extends VerifyManage {

    GalaxyRedisTemplate redisTemplate;

    public void init(){
        redisTemplate = SpringApplicationContext.getBean(GalaxyRedisTemplate.class);
    }

    private String loadClientPublicKey(HttpServletRequest request){
        String clientId = request.getHeader(ClientConstant.CLIENT_ID);
        return redisTemplate.getHashKey(clientId, ClientConstant.PUBLIC_KEY).toString();
    }

    public CreditQualityDto loadCreditQualityDto(HttpServletRequest request, RequestDto dto, Class<CreditQualityDto> clazz){
        String clientPublicKey =this.loadClientPublicKey(request);
        return super.loadRequirementDto(clientPublicKey,dto,clazz);
    }
}
