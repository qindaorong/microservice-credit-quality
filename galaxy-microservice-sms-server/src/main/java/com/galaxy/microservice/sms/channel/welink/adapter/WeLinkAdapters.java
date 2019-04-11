package com.galaxy.microservice.sms.channel.welink.adapter;


import com.galaxy.framework.entity.CodeMessage;
import com.galaxy.framework.exception.BusinessException;
import com.galaxy.framework.redis.components.GalaxyRedisTemplate;
import com.galaxy.framework.verify.JsonUtil;
import com.galaxy.microservice.sms.bean.dto.MessageDto;
import com.galaxy.microservice.sms.bean.dto.SendMessageDto;
import com.galaxy.microservice.sms.bean.dto.SendVerificationDto;
import com.galaxy.microservice.sms.bean.dto.VerificationCodeDto;
import com.galaxy.microservice.sms.channel.welink.client.WeLinkClient;
import com.galaxy.microservice.sms.common.components.SpringApplicationContext;
import com.galaxy.microservice.sms.common.exception.ExceptionCode;
import com.galaxy.microservice.sms.common.utils.HttpClientUtils;
import com.galaxy.microservice.sms.common.utils.XmlUtil;
import com.galaxy.microservice.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WeLinkAdapters implements SmsService {

    private HttpClientUtils httpClientUtils;

    private WeLinkClient weLinkClient;

    @Override
    public Boolean sendVerificationCode(MessageDto<SendVerificationDto> messageDto) {
        Object obj = messageDto.getT();
        String json = JsonUtil.toString(obj);
        SendVerificationDto dto= JsonUtil.parseObject(json,SendVerificationDto.class);
        Boolean flag = sendMessageParam(dto.getMobileNumber(),dto.getMessageContent());
        if(flag){
            return  Boolean.TRUE;
        }else{
            return  Boolean.FALSE;
        }
    }

    @Override
    public void checkVerificationCode(MessageDto<VerificationCodeDto> messageDto) {

        VerificationCodeDto dto = messageDto.getT();
        GalaxyRedisTemplate redisTemplate = SpringApplicationContext.getBean(GalaxyRedisTemplate.class);

        String codeValue = redisTemplate.get(dto.getMobileNumber());
        if (StringUtils.isEmpty(codeValue)) {
            throw new BusinessException(ExceptionCode.CODE_EXPIRATION);
        }
        if (!codeValue.equalsIgnoreCase(dto.getVerificationCode())) {
            throw new BusinessException(ExceptionCode.CODE_ERROR);
        }

        redisTemplate.remove(dto.getMobileNumber());
    }

    @Override
    public void sendMessage(MessageDto<SendMessageDto> messageDto) {
        SendMessageDto dto = messageDto.getT();
        sendMessageParam(dto.getMobileNumber(),dto.getMessageContent());
    }


    public Boolean  sendMessageParam(String mobileNumber,String messageContent){
        Map<String,String> headMap = new HashMap<>();

        Map<String,String> formMap = new HashMap<>(6);

        formMap.put("sname",weLinkClient.getCustomerName());
        formMap.put("spwd", weLinkClient.getCustomerPassword());
        formMap.put("scorpid","");
        formMap.put("sprdid",weLinkClient.getSprdId());
        formMap.put("sdst",mobileNumber);
        formMap.put("smsg",messageContent);
        Response response = httpClientUtils.httpFormPostResponse(weLinkClient.getGetWay(),headMap,formMap);
        String resultXml = "";
        Integer state = 0;
        if(response.isSuccessful()){
            try {
                resultXml = response.body().string();
                log.info("短信接口返回信息 ----> " + resultXml);
                Map<String, Object> resultMap = XmlUtil.xmlToMap(resultXml);
                state = Integer.valueOf(String.valueOf(resultMap.get("State")));
                if(state != 0){
                    CodeMessage codeMessage = new CodeMessage();
                    codeMessage.setCode(6010);
                    codeMessage.setMessage(String.valueOf(resultMap.get("MsgState")));
                    throw  new BusinessException(codeMessage);
                }else{
                    return Boolean.TRUE;
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw  new BusinessException(ExceptionCode.SMS_SERVICE_ERROR);
            }finally {
                response.close();
            }
        }
        return Boolean.FALSE;
    }

    private WeLinkAdapters(){
        if(WelinkServiceHolder.WELINK_ADAPTER != null){
            throw new RuntimeException("multiple instances are not allowed");
        }

        httpClientUtils = SpringApplicationContext.getBean(HttpClientUtils.class);
        weLinkClient = SpringApplicationContext.getBean(WeLinkClient.class);
    }

    public static final WeLinkAdapters getInstance(){
        //在返回结果以前，一定会先加载内部类
        return WelinkServiceHolder.WELINK_ADAPTER;
    }

    private static class WelinkServiceHolder{
        private static final WeLinkAdapters WELINK_ADAPTER = new WeLinkAdapters();
    }
}
