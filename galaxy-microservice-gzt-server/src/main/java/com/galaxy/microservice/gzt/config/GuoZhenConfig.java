package com.galaxy.microservice.gzt.config;

import cn.id5.gboss.GbossClient;
import cn.id5.gboss.GbossConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName： GuoZhenConfig
 * @Description
 * @Author alan qin
 * @Date 2019-04-02
 **/
@Configuration
@EnableConfigurationProperties(GuoZhenProperties.class)
public class GuoZhenConfig {

    @Autowired
    GuoZhenProperties guoZhenProperties;

    @Bean
    @ConditionalOnMissingBean
    public GbossClient createClient() {
        GbossConfig config = new GbossConfig();
        config.setEndpoint(guoZhenProperties.getEndpoint());
        config.setDesKey(guoZhenProperties.getDesKey());
        config.setEncrypted(true);
        config.setAccount(guoZhenProperties.getAccount());
        config.setAccountpwd(guoZhenProperties.getAccountPassword());
        config.setDesCharset(guoZhenProperties.getDesCharset());
        config.setTimeout(15000);
        return new GbossClient(config);
    }

}
