package com.galaxy.microservice.sms.service;

import com.alibaba.fastjson.JSONObject;
import com.galaxy.framework.exception.BusinessException;
import com.galaxy.framework.redis.components.GalaxyRedisTemplate;
import com.galaxy.framework.verify.JsonUtil;
import com.galaxy.microservice.sms.bean.dto.*;
import com.galaxy.microservice.sms.common.aop.annotation.MethodCheck;
import com.galaxy.microservice.sms.common.exception.ExceptionCode;
import com.galaxy.microservice.sms.enums.ChannelStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

/**
 * @ClassName： PassportForMessageAdapter
 * @Description
 * @Author alan qin
 * @Date 2019-04-10
 **/
@Service
public class PassportForMessageAdapter {

    @Autowired
    GalaxyRedisTemplate redisTemplate;

    @MethodCheck
    public Boolean sendVerificationCode(MessageDto<SendVerificationDto> messageDto) {
        Object obj = messageDto.getT();
        String json = JsonUtil.toString(obj);
        SendVerificationDto dto= JsonUtil.parseObject(json,SendVerificationDto.class);
        SmsService smsService = ChannelStrategy.getSmsService(dto.getMessageChannel());
        Boolean flag = smsService.sendVerificationCode(messageDto);

        if (flag) {
            //save verificationCode and mobileNumber into redis
            redisTemplate.setForTimeMIN(dto.getMobileNumber(), dto.getVerificationCode(), dto.getEffectiveTime());
        } else {
            throw new BusinessException(ExceptionCode.MESSAGE_SERVICE_ERROR);
        }
        return  Boolean.TRUE;
    }

    @MethodCheck
    public void checkVerificationCode(MessageDto<VerificationCodeDto> messageDto) {
        VerificationCodeDto dto =messageDto.getT();
        ChannelStrategy.getSmsService(dto.getMessageChannel()).checkVerificationCode(messageDto);
    }

    @MethodCheck
    public void sendMessage(MessageDto<SendMessageDto> messageDto) {
        SendMessageDto dto =messageDto.getT();
        ChannelStrategy.getSmsService(dto.getMessageChannel()).sendMessage(messageDto);
    }

}
