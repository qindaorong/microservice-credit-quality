### 聚合服务查询框架之国政通服务
> 聚合支付框架下的国政通个人信用查询服务

#### 聚合结构介绍
+ ___bean___
    + dto（入参dto）
    + vo（展示vo）
+ ___common___（工厂方法实现包）
    + components（对象组件）
    + constants（常量对象）
+ ___config___（服务配置文件）
+ ___controller___（对外提供服务）
+ ___entity___（orm操作pojo对象）
+ ___service___（服务service操作）

### 工程依赖说明
> 项目依赖框架 galaxy-framework-muster 所提供的基础服务

```
	    <!--Web-->
        <dependency>
            <groupId>com.galaxy.app</groupId>
            <artifactId>galaxy-framework-web</artifactId>
            <version>1.0.0</version>
        </dependency>
        <!--verify-->
        <dependency>
            <groupId>com.galaxy.app</groupId>
            <artifactId>galaxy-framework-verify</artifactId>
            <version>1.0.0</version>
        </dependency>
        <!--orm-->
        <dependency>
            <groupId>com.aggregate.app</groupId>
            <artifactId>galaxy-framework-orm</artifactId>
            <version>1.0.0</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-data-mongodb</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

```


### 部署及调用规则
* 部署

    a. 由于项目为微服务架构，需要部署为单体jar或者使用docker容器化部署。
    
    b. 修改.yml文件的配置，并启动
    
* 调用

    a. 使用同一微服务框架gateway作为项目主网关，完成调用客户端身份认定以及服务访问熔断。
    
    b.单体微服务完成本服务自身的验证签名检验，并完成真正的服务调用。
