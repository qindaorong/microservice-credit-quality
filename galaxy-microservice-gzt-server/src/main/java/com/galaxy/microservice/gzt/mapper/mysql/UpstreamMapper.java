package com.galaxy.microservice.gzt.mapper.mysql;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.galaxy.microservice.gzt.entity.mysql.Upstream;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UpstreamMapper extends BaseMapper<Upstream> {
}
