package com.galaxy.microservice.sms.service;

import com.galaxy.microservice.sms.bean.dto.MessageDto;
import com.galaxy.microservice.sms.bean.dto.SendMessageDto;
import com.galaxy.microservice.sms.bean.dto.SendVerificationDto;
import com.galaxy.microservice.sms.bean.dto.VerificationCodeDto;

public interface SmsService {
    /**
     * 发送短信验证码
     * @param messageDto
     */
    Boolean  sendVerificationCode(MessageDto<SendVerificationDto> messageDto);

    /**
     * 验证验证码信息
     * @param messageDto
     */
    void  checkVerificationCode(MessageDto<VerificationCodeDto> messageDto);


    /**
     * 发送短信信息
     * @param messageDto
     */
    void  sendMessage(MessageDto<SendMessageDto> messageDto);
}
