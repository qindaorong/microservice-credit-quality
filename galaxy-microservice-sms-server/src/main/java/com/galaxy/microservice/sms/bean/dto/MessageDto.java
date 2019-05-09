package com.galaxy.microservice.sms.bean.dto;

import lombok.*;

/**
 * @ClassName： MessageDto
 * @Description
 * @Author alan qin
 * @Date 2019-04-10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class MessageDto<T> {

    /**
     * 第三方查询流水号
     */
    private String outerId;

    /**
     * 用户上游clientId
     */
    private String clientId;


    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 具体业务实体
     */
    private T t;


    @Override
    public String toString() {
        return "MessageDto{" +
                "outerId='" + outerId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", serverName='" + serverName + '\'' +
                ", t=" + t +
                '}';
    }

}
