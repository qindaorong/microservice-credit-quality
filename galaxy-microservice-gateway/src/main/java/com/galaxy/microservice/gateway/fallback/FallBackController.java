package com.galaxy.microservice.gateway.fallback;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @ClassName： FallBackController
 * @Description
 * @Author alan qin
 * @Date 2019-04-03
 **/
@RestController
public class FallBackController {

    @RequestMapping("/gzt/fallback")
    public Mono<String> gztFallback() {
        return Mono.just("[gzt] service error, jump fallback");
    }
}
