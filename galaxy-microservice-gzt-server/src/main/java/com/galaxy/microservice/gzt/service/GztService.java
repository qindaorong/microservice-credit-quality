package com.galaxy.microservice.gzt.service;

import com.galaxy.microservice.gzt.bean.dto.CreditQualityDto;
import com.galaxy.microservice.gzt.bean.vo.DataResponseVO;

public interface GztService {

    DataResponseVO queryCreditQuality(CreditQualityDto creditQualityDto);
}
