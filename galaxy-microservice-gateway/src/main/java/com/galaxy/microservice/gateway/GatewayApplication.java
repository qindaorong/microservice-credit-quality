package com.galaxy.microservice.gateway;


import com.galaxy.microservice.gateway.common.filters.AuthorizeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

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

/*	@Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/gzt/**")
                        .filters(f -> f.filter(new AuthorizeFilter()))
                        .uri("lb://SERVER-GZT")
                        .order(0)
                        .id("customer_header_filter_router")
                )
                .build();

    }*/

}
