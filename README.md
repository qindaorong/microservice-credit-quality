### 聚合服务查询框架
> 以springCloud为基础架构完成搭建的微服务集成运营框架

#### 聚合服务redis数据结构，以及数据保存规范
* 网关层接入方
    + ___up0001___(用户client_id)
        + client_secret: ***************************** (对应用户client_id的client_secret加密字符串)

* 服务层验签
    + ___up0001___(用户client_id)
        + public_key: ***************************** (对应用户client_id的public_key)
        + 其他: ***************************** (对应各个业务自定义数据)

### 调用方接入流程
*   通过各个微服务web端完成上游接入，由web端给用户分配以下数据：
    + client_id (上游用户调用API唯一标识)
    + client_secret(上游用户调用API加密标识)
    + private_key(上游用户调用API,签名和参数数据加密用)
    + public_key(服务端验证和解密上游用户调用)
    
    以上数据接入方获得：
    + client_id
    + client_secret
    + private_key
    
    服务端通过邮件将以上数据发送给用户。  
    
    
*  服务调用通过【聚合服务查询】服务方提供的client.jar程序完成api调用，用户通过使用jar包中的AggregateCreditClient类中的sendHttpRequest方法完成对openAPI的调用。
    
    客户需要在自己项目资源resources中新建配置文件【aggregate-credit.properties】
    
    ```
    aggregate.server.openApi.ip=127.0.0.1
    aggregate.server.openApi.port=8080
    aggregate.server.openApi.privateKey=MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJqIbiNEYXMoQ/IpP8sZg7VoO9gtCqyw3neOobtFHI3Z959lnvO4QQrXRm8q264GlmHyu3FhYz0lp9rBidsFtXJ3ZJ4foCE+BLj186E/V40RRDchFlxzrUYItPtngZpgxaO/Zn/2cMzOqVTvPu97rdw+CYLwS/VaeYkjUU6Nr4CzAgMBAAECgYAjDaN+GHrKdm3DNpwj4+u+cKByjvUG6y/ris0g/7JUcwb6f0CpJDiMzvxJJz2hohjmqvrd4ytxqWMD+jbdGdRogd0XQlEbV2/RayhSM0p2AfCvNBRlrkx0EwsxwtMVT9zX1Iw8XfzP2124dtcDJAjFCOqt74JsK32SRnpCkZdIkQJBAN8D4BjTjQLmAV6oxBtkzilBANSdBtV3g73lumuJOfXCVvrIIfNAmPauZe/wOLwTnIdhBHuGIxCJGh9apyISPE8CQQCxY5ThN7sbAEGuwFGXlvj3iJUaBlcNc78QPO9BY4UvSh2pybZz/ylEaGPYkgMNO+5Vti/S9nUpbmxCZVRRGOhdAkEAjEKhtLuImmQSRHicLiZuSx/o58+Ctssd/lb3sh5yZ5C8p6krQRRFoA/7aLaK2C71aWepLA8nCoVP+pxHNXSGnQJAQaofPo+mz4P0zH+ctITKfLcumoemSfCC1bv4xTfV4X+KI4Pr6lyWJiCOdWj3gDqjK09ZvLpG/KNHp/xijfKucQJACXYrbzbINF+iweHPacTj7iHNJo6HET7nT+u/gzHUCtp5+TL2OJ9Vil73z3TzPa8nvdrns51piErRrke3yNhuNg==
    aggregate.server.openApi.clientId=*****
    aggregate.server.openApi.clientSecret=*****
    
    ```
    
    如果有环境区分请将【aggregate-credit.properties】做多份并并将环境配置项配置在文件名字中，如：
    
    + dev(环境)
        + aggregate-credit-dev.properties
    + test(环境)
        + aggregate-credit-test.properties   
        
    以此类推。 

#### 其他
> 目前项目仍处于开发阶段，文档尚在补全中，如果有纰漏，请直接联系开发人员

+ 安邦 <bangan@galaxyf.com>
+ 秦道荣 <daorongqin@galaxyf.com>
+ 马涛 <taoma@galaxyf.com>

    

