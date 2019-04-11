package com.galaxy.microservice.sms.common.config;

import com.galaxy.microservice.sms.channel.welink.client.WeLinkClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName： WeLinkConfig
 * @Description
 * @Author alan qin
 * @Date 2019-04-02
 **/
@Configuration
@EnableConfigurationProperties(WeLinkProperties.class)
public class WeLinkConfig {

    @Autowired
    WeLinkProperties weLinkProperties;

    @Bean
    @ConditionalOnMissingBean
    public WeLinkClient createClient() {
        WeLinkClient client = new WeLinkClient();
        BeanUtils.copyProperties(weLinkProperties,client);
        return client;
    }


}
