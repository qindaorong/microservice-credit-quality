package com.galaxy.microservice.gzt.service.impl;

import cn.id5.gboss.GbossClient;
import cn.id5.gboss.GbossConfig;
import com.galaxy.framework.utils.JsonUtil;
import com.galaxy.microservice.gzt.bean.dto.CreditQualityDto;
import com.galaxy.microservice.gzt.bean.dto.GuoZhenDto;
import com.galaxy.microservice.gzt.bean.dto.QueryCreditDto;
import com.galaxy.microservice.gzt.bean.vo.DataResponseVO;
import com.galaxy.microservice.gzt.service.GztService;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

@Slf4j
@Service
public class GztServiceImpl implements GztService {

    @Autowired
    GbossClient client;

    @Override
    public DataResponseVO queryCreditQuality(CreditQualityDto creditQualityDto) {

        GuoZhenDto<QueryCreditDto> guoZhenDto = convert2GuoZhenDto(creditQualityDto);

        StringJoiner sj =new StringJoiner(",", "", "");
        sj.add(guoZhenDto.getOuterId()).add(guoZhenDto.getT().getName()).add(guoZhenDto.getT().getIdentityId());
        log.debug("[StringJoiner] is [{}]",sj.toString());
        try {
/*            HttpResponseData httpData = client.invokeSingle(guoZhenProperties.getProduct(), sj.toString());
            log.debug("[GuoZhenChannel] get HttpResponseData is : [{}]", httpData.getData());
            if (httpData.getStatus() == HttpStatus.SC_OK) {
                return loadResponseDate(httpData.getData(), guoZhenDto);
            }*/
            DataResponseVO dataResponseVO = DataResponseVO.builder()
                    .outerId(guoZhenDto.getOuterId())
                    .clientId(guoZhenDto.getClientId())
                    .data("<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\r\\n<data>\\r\\n  <message>\\r\\n    <status>0</status>\\r\\n    <value>处理成功</value>\\r\\n  </message>\\r\\n  <attentionScores>\\r\\n    <attentionScore>\\r\\n      <code>1</code>\\r\\n      <message>评分成功</message>\\r\\n      <wybs>ttt111112</wybs>\\r\\n      <inputXm>马涛</inputXm>\\r\\n      <inputZjhm>610429199009085178</inputZjhm>\\r\\n      <score>0</score>\\r\\n    </attentionScore>\\r\\n  </attentionScores>\\r\\n</data>\\r\\n\\r\\n")
                    .build();
            return dataResponseVO;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    private GuoZhenDto<QueryCreditDto> convert2GuoZhenDto(CreditQualityDto creditQualityDto) {
        String dateStr = creditQualityDto.getData();

        QueryCreditDto queryCreditDto = JsonUtil.parseObject(dateStr, QueryCreditDto.class);

        GuoZhenDto guoZhenDto = new GuoZhenDto();
        BeanUtils.copyProperties(creditQualityDto, guoZhenDto);
        guoZhenDto.setT(queryCreditDto);
        return guoZhenDto;
    }


    private DataResponseVO loadResponseDate(String data,GuoZhenDto<QueryCreditDto> guoZhenDto) {
        try {
            Element doc = DocumentHelper.parseText(data).getRootElement().element("attentionScores").element("attentionScore");
            Element statusDoc = DocumentHelper.parseText(data).getRootElement().element("message");
            Integer status = (Integer) statusDoc.element("status").getData();
            String code =  doc.element("code").getData().toString();
            String score = doc.element("score").getData().toString();
            DataResponseVO dataResponseVO = DataResponseVO.builder()
                    .outerId(guoZhenDto.getOuterId())
                    .clientId(guoZhenDto.getClientId())
                    .data(data)
                    .build();

            return dataResponseVO;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


}
