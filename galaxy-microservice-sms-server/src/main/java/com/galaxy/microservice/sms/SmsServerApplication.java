package com.galaxy.microservice.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
//@EnableEurekaClient
@EnableCircuitBreaker //对HystrixR熔断机制的支持
public class SmsServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(SmsServerApplication.class)
				.main(SmsServerApplication.class)
				.run(args);
		log.info("----SmsServerApplication Start PID={}----", new SmsServerApplication().toString());
		context.registerShutdownHook();
	}

}
