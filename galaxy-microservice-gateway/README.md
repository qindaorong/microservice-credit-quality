### 聚合服务查询框架之网关
> 聚合支付框架下的网关服务

#### 聚合结构介绍

#### 工程依赖说明
> 项目依赖框架 galaxy-framework-muster 所提供的基础服务

```
		<!--Eureka Client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <!--redis-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

```
####添加新服务路由


```
            routes:
                - id: path_route
                  uri: lb://SERVER-GZT
                  predicates:
                      - Path=/gzt/**
                      - Method= POST
                      - Host= **.galaxy.com
                  filters:
                      - StripPrefix=1
                      # 调用级别限流
                      - name: Hystrix
                        args:
                            name: fallbackcmd
                            fallbackUri: forward:/gzt/fallback

```

#### 部署及调用规则
* 部署

    a. 由于项目为微服务架构，需要部署为单体jar或者使用docker容器化部署。
    
    b. 修改.yml文件的配置，并启动
    
* 调用

    a. 整个微服务使用统一gateway作为项目主网关，完成调用客户端身份认定以及服务访问熔断。
    
    b. 在getway层完成对服务调用方身份验证，各个服务自己完成各自参数的验签和DTO转换。
    
    c. 统一 header 层参数为client_id和client_secret的加密后字符串。sign和数据封装，统一封装在request中，并通过json的格式发送给网关。如果验签通过，框架会根据参数情况完成数据转换。
    
    
    
 ### 数据结构设想说明
> redis 网关级别数据结构
+ ___client_id___(每个用户的client_id)
    + client_secret（每个用户对应每个用户的client_id的secret，保存数据为加密后字符串）
