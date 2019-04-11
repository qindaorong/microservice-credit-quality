package com.galaxy.microservice.sms.enums;

import com.galaxy.microservice.sms.channel.welink.adapter.WeLinkAdapters;
import com.galaxy.microservice.sms.service.SmsService;
import lombok.Getter;

@Getter
public enum ChannelStrategy {

    WELINK(WeLinkAdapters.getInstance(),"welink");

    private SmsService strategy;

    private String name;

    ChannelStrategy(SmsService smsService,String name ) {
        this.strategy = smsService;
        this.name = name;
    }

    public static SmsService getSmsService(String name) {
        ChannelStrategy[] channelStrategies = values();
        for (ChannelStrategy channelStrategy : channelStrategies) {
            if (channelStrategy.getName().equals(name)) {
               return channelStrategy.getStrategy();
            }
        }
        return null;
    }
}
