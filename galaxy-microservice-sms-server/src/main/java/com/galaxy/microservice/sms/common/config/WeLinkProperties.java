package com.galaxy.microservice.sms.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName： WeLinkProperties
 * @Description
 * @Author alan qin
 * @Date 2019-04-02
 **/
@Data
@ConfigurationProperties(prefix = "channel.welink")
public class WeLinkProperties {
    private String getWay;
    private String sprdId;
    private String verification;
    private String customerName;
    private String customerPassword;
}
