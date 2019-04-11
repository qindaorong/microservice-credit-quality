package com.galaxy.microservice.sms.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMessageDto extends BaseDto {

    /**
     * 短信通道 messageChannelEnums
     */
    private String messageChannel;

    /**
     * 消息接收人手机号码
     */
    private String mobileNumber;

    /**
     * 消息主体
     */
    private String messageContent;
}
