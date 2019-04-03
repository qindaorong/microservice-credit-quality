package com.galaxy.microservice.gzt.bean.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpstreamVO {

    private Integer id;

    private String userId;

    private String publicKey;

    private String clientId;

    private String clientSecret;
}
