package com.galaxy.microservice.gzt.controller;


import com.galaxy.framework.entity.ResponseResult;
import com.galaxy.framework.web.common.WebResCallback;
import com.galaxy.framework.web.common.WebResCriteria;
import com.galaxy.microservice.gzt.bean.dto.CreditQualityDto;
import com.galaxy.microservice.gzt.service.GztService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CreditQualityController {

    @Autowired
    GztService gztService;

    @PostMapping(value =  "/personCreditQuality")
    public ResponseResult personCreditQuality(
            @ApiParam(required = true, name = "requestDto", value = "查询请求dto")
            @RequestBody @Validated final CreditQualityDto creditQualityDto
    ) {
        return new WebResCallback() {
            @Override
            public void execute(WebResCriteria criteria, Object... params) {
                criteria.addSingleResult(creditQualityDto);
            }
        }.sendRequest(creditQualityDto);
    }


    /** *
     *@描述   程序级别报错断融测试
     *@参数
     *@返回值  java.lang.String
     *@创建人  alan qin
     *@创建时间  04/04/2019
     *@修改人和其它信息
     **/
    @GetMapping(value =  "/hi")
    @HystrixCommand(fallbackMethod = "sayHiFallback")
    public ResponseResult sayHi() {
        return new WebResCallback() {
            @Override
            public void execute(WebResCriteria criteria, Object... params) {
                criteria.addSingleResult("hello world!");
            }
        }.sendRequest();
    }

    public ResponseResult sayHiFallback() {
        return ResponseResult.fail(500,"sayHiFallback");
    }
}