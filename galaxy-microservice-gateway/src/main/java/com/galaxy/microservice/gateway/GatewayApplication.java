package com.galaxy.microservice.gateway;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(GatewayApplication.class)
				.main(GatewayApplication.class)
				.run(args);
		log.info("----GatewayApplication Start PID={}----", new GatewayApplication().toString());
		context.registerShutdownHook();
	}
}
