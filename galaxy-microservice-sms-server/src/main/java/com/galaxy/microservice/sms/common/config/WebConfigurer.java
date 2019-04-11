package com.galaxy.microservice.sms.common.config;

import com.galaxy.microservice.sms.common.interceptors.ServiceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName： WebConfigurer
 * @Description
 * @Author alan qin
 * @Date 2019-04-10
 **/
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private ServiceInterceptor interceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/api/**");
    }
}
