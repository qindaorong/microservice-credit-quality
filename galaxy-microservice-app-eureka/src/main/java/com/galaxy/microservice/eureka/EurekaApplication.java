package com.galaxy.microservice.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder()
				.sources(EurekaApplication.class)
				.main(EurekaApplication.class)
				.run(args);
		log.info("----EurekaApplication Start PID={}----", new EurekaApplication().toString());
		context.registerShutdownHook();
	}
}
