package com.galaxy.microservice.sms.channel.welink.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName： WeLinkProperties
 * @Description
 * @Author alan qin
 * @Date 2019-04-02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeLinkClient {
    private String getWay;
    private String sprdId;
    private String verification;
    private String customerName;
    private String customerPassword;
}
