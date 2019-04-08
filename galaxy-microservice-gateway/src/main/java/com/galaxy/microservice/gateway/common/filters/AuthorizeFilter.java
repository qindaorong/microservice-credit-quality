package com.galaxy.microservice.gateway.common.filters;

import com.galaxy.microservice.gateway.common.components.RedisHandler;
import com.galaxy.microservice.gateway.common.constants.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName： AuthorizeFilter
 * @Description
 * @Author alan qin
 * @Date 2019-04-04
 **/
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {

    @Autowired
    RedisHandler redisHandler;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response =exchange.getResponse();
        Map<String,String> requestSecurityValue = this.getHeaderMap(request);

        if(!checkHeaderSecurityKey(requestSecurityValue)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }


    /** *
     *@描述   检查Header 中安全参数clientId和clientSecret
     *@参数  [headerMap]
     *@返回值  java.lang.Boolean
     *@创建人  alan qin
     *@创建时间  04/04/2019
     *@修改人和其它信息
     **/
    private Boolean checkHeaderSecurityKey(Map<String,String> headerMap){
        String clientId = headerMap.get(SecurityConstant.REQUEST_SECURITY_CLIENT_ID);
        String clientSecret = headerMap.get(SecurityConstant.REQUEST_SECURITY_CLIENT_SECRET);

        String decodeSecret = String.valueOf(redisHandler.getHashKey(clientId, SecurityConstant.REQUEST_SECURITY_CLIENT_SECRET));

        if(StringUtils.equals(decodeSecret,clientSecret)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /** *
     *@描述   获得用户验证信息
     *@参数  [request]
     *@返回值  java.util.Map<java.lang.String,java.lang.String>
     *@创建人  alan qin
     *@创建时间  04/04/2019
     *@修改人和其它信息
     **/
    private Map<String,String> getHeaderMap(ServerHttpRequest request){
        Map<String,String> headerMap = new HashMap<>(2);
        String clientId = request.getHeaders().getFirst(SecurityConstant.REQUEST_SECURITY_CLIENT_ID);
        String clientSecret = request.getHeaders().getFirst(SecurityConstant.REQUEST_SECURITY_CLIENT_SECRET);

        headerMap.put(SecurityConstant.REQUEST_SECURITY_CLIENT_ID,clientId);
        headerMap.put(SecurityConstant.REQUEST_SECURITY_CLIENT_SECRET,clientSecret);
        return headerMap;
    }

}
