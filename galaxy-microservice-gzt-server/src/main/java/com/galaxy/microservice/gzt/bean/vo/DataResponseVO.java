package com.galaxy.microservice.gzt.bean.vo;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DataResponseVO implements Serializable {

    private static final long serialVersionUID = 8170829250230985443L;
    /**
     * 第三方查询流水号
     */
    private String outerId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * data
     */
    private String data;


}
