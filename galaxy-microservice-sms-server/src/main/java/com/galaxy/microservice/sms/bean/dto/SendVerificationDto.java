package com.galaxy.microservice.sms.bean.dto;

import com.galaxy.framework.util.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "发送短信验证码Dto")
public class SendVerificationDto extends BaseDto {

    /**
     * 短信通道 messageChannelEnums
     */
    @ApiModelProperty(value = "通道id (目前请传值:{51welink} 通道,更多通道请查看 ChannelEnum 枚举类)")
    private String messageChannel;

    /**
     * 消息接收人手机号码
     */
    @ApiModelProperty(value = "消息接收人手机号码")
    private String mobileNumber;

    /**
     *  消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String messageContent;

    /**
     * 验证码消息
     */
    @ApiModelProperty(value = "验证码消息")
    private String verificationCode;


    @ApiModelProperty(value = "有效时间，默认值10")
    private Integer effectiveTime = 10 ;

    public static void main(String[] args) {
        SendVerificationDto dto = SendVerificationDto.builder()
                .effectiveTime(10)
                .verificationCode("1234")
                .messageContent("【有趣花】您的验证码为：1512,有效时间5分钟")
                .mobileNumber("18165288627")
                .messageChannel("welink").build();


        MessageDto messageDto = MessageDto.builder()
                .clientId("up_0001")
                .outerId("outerId")
                .serverName("server-sms")
                .t(dto).build();

        String str = JsonUtil.toString(messageDto);

        System.out.println("MessageDto :"+ str);
    }
}
