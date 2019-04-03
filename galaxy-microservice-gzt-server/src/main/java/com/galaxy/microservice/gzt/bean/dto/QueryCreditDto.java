package com.galaxy.microservice.gzt.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryCreditDto {
    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户id
     */
    private String identityId;

}
