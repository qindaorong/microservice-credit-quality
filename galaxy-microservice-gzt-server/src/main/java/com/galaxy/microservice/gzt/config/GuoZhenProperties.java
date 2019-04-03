package com.galaxy.microservice.gzt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName： GuoZhenProperties
 * @Description
 * @Author alan qin
 * @Date 2019-04-02
 **/
@Data
@ConfigurationProperties(prefix = "channel.guozhen")
public class GuoZhenProperties {
    private String endpoint;
    private String desKey;
    private String account;
    private String accountPassword;
    private String desCharset;
    private String product;
}
