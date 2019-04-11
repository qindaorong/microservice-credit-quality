package com.galaxy.microservice.sms.controller;


import com.galaxy.framework.bean.dto.RequestDto;
import com.galaxy.framework.entity.ResponseResult;
import com.galaxy.framework.web.common.WebResCallback;
import com.galaxy.framework.web.common.WebResCriteria;
import com.galaxy.microservice.sms.bean.dto.MessageDto;
import com.galaxy.microservice.sms.bean.dto.SendMessageDto;
import com.galaxy.microservice.sms.bean.dto.SendVerificationDto;
import com.galaxy.microservice.sms.bean.dto.VerificationCodeDto;
import com.galaxy.microservice.sms.service.PassportForMessageAdapter;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class MessageController extends BaseController{


    @Autowired
    PassportForMessageAdapter passportForMessageAdapter;

    @PostConstruct
    @Override
    public void init(){
        super.init();
    }

    @PostMapping(value =  "/sendMessage")
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseResult sendMessage(
            final HttpServletRequest request,
            @ApiParam(required = true, name = "requestDto", value = "请求dto")
            @RequestBody @Validated final RequestDto requestDto
    ) {
        return new WebResCallback() {
            @Override
            public void execute(WebResCriteria criteria, Object... params) {
                MessageDto<SendMessageDto> messageDto =  loadDto(request,requestDto,MessageDto.class);
                passportForMessageAdapter.sendMessage(messageDto);
            }
        }.sendRequest(requestDto);
    }

    @PostMapping(value =  "/sendVerificationCode")
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseResult sendVerificationCode(
            final HttpServletRequest request,
            @ApiParam(required = true, name = "requestDto", value = "请求dto")
            @RequestBody @Validated final RequestDto requestDto
    ) {
        return new WebResCallback() {
            @Override
            public void execute(WebResCriteria criteria, Object... params) {
                MessageDto<SendVerificationDto> messageDto =  loadDto(request,requestDto,MessageDto.class);
                passportForMessageAdapter.sendVerificationCode(messageDto);
            }
        }.sendRequest(requestDto);
    }

    @PostMapping(value =  "/checkVerificationCode")
    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseResult checkVerificationCode(
            final HttpServletRequest request,
            @ApiParam(required = true, name = "requestDto", value = "请求dto")
            @RequestBody @Validated final RequestDto requestDto
    ) {
        return new WebResCallback() {
            @Override
            public void execute(WebResCriteria criteria, Object... params) {
                MessageDto<VerificationCodeDto> messageDto =  loadDto(request,requestDto,MessageDto.class);
                passportForMessageAdapter.checkVerificationCode(messageDto);
            }
        }.sendRequest(requestDto);
    }



    public ResponseResult fallback(final HttpServletRequest request,@RequestBody @Validated final RequestDto requestDto) {
        return ResponseResult.fail(500,"fallback");
    }
}