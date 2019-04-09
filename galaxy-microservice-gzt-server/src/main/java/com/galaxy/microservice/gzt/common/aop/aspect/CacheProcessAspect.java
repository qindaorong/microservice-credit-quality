package com.galaxy.microservice.gzt.common.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.galaxy.framework.exception.BusinessException;
import com.galaxy.framework.redis.components.GalaxyRedisTemplate;
import com.galaxy.framework.verify.DateUtil;
import com.galaxy.framework.verify.JsonUtil;
import com.galaxy.microservice.gzt.bean.dto.CreditQualityDto;
import com.galaxy.microservice.gzt.bean.vo.DataResponseVO;
import com.galaxy.microservice.gzt.common.aop.annotation.CacheProcess;
import com.galaxy.microservice.gzt.common.components.RedisDistributedLock;
import com.galaxy.microservice.gzt.common.constants.ClientConstant;
import com.galaxy.microservice.gzt.common.exceptions.ExceptionChannelCode;
import com.galaxy.microservice.gzt.entity.mongo.CreditQualityMongo;
import com.galaxy.microservice.gzt.mapper.mongodb.CreditQualityMongoDao;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName： CacheProcessAspect
 * @Description
 * @Author alan qin
 * @Date 2019-04-09
 **/
@Aspect
@Component
@Slf4j
public class CacheProcessAspect {

    @Autowired
    GalaxyRedisTemplate redisTemplate;

    @Autowired
    CreditQualityMongoDao creditQualityMongoDao;

    @Autowired
    RedisDistributedLock redisDistributedLock;

    private final String LOCK_KEY_PREFIX="redis_lock_key_";

    @Pointcut(" args(com.galaxy.microservice.gzt.bean.dto.CreditQualityDto) && @annotation(com.galaxy.microservice.gzt.common.aop.annotation.CacheProcess)")
    public void pointCut(){}


    /** *
     *@描述   前置查询
     *@参数  [joinPoint]
     *@返回值  void
     *@创建人  alan qin
     *@创建时间  04/09/2019
     *@修改人和其它信息
     **/
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        CreditQualityDto creditQualityDto;
        if(!CollectionUtils.isEmpty(Lists.newArrayList(joinPoint.getArgs()))){
           creditQualityDto  = (CreditQualityDto)joinPoint.getArgs()[0];
        }else{
            throw new BusinessException(ExceptionChannelCode.PARAMETER_MISMATCH);
        }
        Map<String, Object> maps = this.isChannelOpen(creditQualityDto,methodName);
        if(!maps.containsKey(methodName)){
            throw new BusinessException(ExceptionChannelCode.CHANNEL_NOT_OPEN);
        }
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint)throws Throwable{
        CacheProcess cacheProcess = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(CacheProcess.class);
        String methodName = joinPoint.getSignature().getName();
        Object rvt = null;

        if(!CollectionUtils.isEmpty(Lists.newArrayList(joinPoint.getArgs()))){
            CreditQualityDto creditQualityDto  = (CreditQualityDto)joinPoint.getArgs()[0];
            String clientId = creditQualityDto.getClientId();
            String lockKey =LOCK_KEY_PREFIX.concat(clientId) ;

            try {
                redisDistributedLock.lock(lockKey,RedisDistributedLock.expireMsecs,RedisDistributedLock.timeoutMsecs);
                //channel check
                Map<String, Object> maps = this.isChannelOpen(creditQualityDto,methodName);

                //amount check
                if( cacheProcess.amountSwitch()){
                    BigDecimal balance = BigDecimal.valueOf(Double.valueOf(maps.get(ClientConstant.CHANNEL_AMOUNT).toString()));

                    if(BigDecimal.ZERO.compareTo(balance) == 0){
                        throw new BusinessException(ExceptionChannelCode.BALANCE_ENOUGH);
                    }
                }

                //cache check
                if(cacheProcess.cacheSwitch()){
                    List<CreditQualityMongo> list = creditQualityMongoDao.findByClientIdAndServerNameOrderByDateTimeDesc(creditQualityDto.getClientId(),methodName);

                    if(!CollectionUtils.isEmpty(list)){
                        CreditQualityMongo creditQualityMongo = list.get(0);
                        Date dataTime = creditQualityMongo.getDateTime();
                        Long day = DateUtil.getDiffDays(new Date(),dataTime);
                        if(day <= 2){

                            DataResponseVO dataResponseVO = DataResponseVO.builder()
                                    .clientId(creditQualityMongo.getClientId())
                                    .outerId(creditQualityMongo.getOutId())
                                    .data(creditQualityMongo.getQualityData())
                                    .build();

                            return dataResponseVO;
                        }else{
                            creditQualityMongoDao.deleteByClientIdAndAndServerName(creditQualityDto.getClientId(),methodName);
                        }
                    }
                }

                //do method request third api
                rvt = joinPoint.proceed();

                //deduction user amount
                if(cacheProcess.amountSwitch()){
                    this.deductionAmount(creditQualityDto,methodName);
                }

                if(rvt != null){

                    DataResponseVO dataResponseVO = (DataResponseVO)rvt;
                    CreditQualityMongo creditQualityMongo = CreditQualityMongo.builder()
                            .clientId(dataResponseVO.getClientId())
                            .dateTime(new Date())
                            .serverName(methodName)
                            .qualityData(dataResponseVO.getData())
                            .outId(dataResponseVO.getOuterId())
                            .build();

                    creditQualityMongoDao.save(creditQualityMongo);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                redisDistributedLock.unlock(lockKey);
            }
        }
        return rvt;
    }


    /** *
     *@描述   获得返回值打印
     *@参数  [joinPoint]
     *@返回值  void
     *@创建人  alan qin
     *@创建时间  04/09/2019
     *@修改人和其它信息
     **/
    @AfterReturning(pointcut = "pointCut()",returning="rvt")
    public void doAfterReturning(JoinPoint joinPoint,Object rvt) {
        if(rvt instanceof DataResponseVO ){
            DataResponseVO dataResponseVO = (DataResponseVO)rvt;
            log.info("client [{}] call api has bean return. value is [{}] ",dataResponseVO.getClientId(),dataResponseVO.getData());
        }
    }


    /** *
     *@描述   异常处理机制
     *@参数  [joinPoint]
     *@返回值  void
     *@创建人  alan qin
     *@创建时间  04/09/2019
     *@修改人和其它信息
     **/
    @AfterThrowing(pointcut = "pointCut()", throwing="ex")
    public void doThrowing(JoinPoint joinPoint,Throwable  ex) {
        //log Exceptions
        CreditQualityDto creditQualityDto  = (CreditQualityDto)joinPoint.getArgs()[0];
        log.error("client [{}] calling has Exceptions. \n request data is [{}] \n exception message is [{}] \n",creditQualityDto.getClientId(),creditQualityDto.getData(),ex.getMessage());
    }


    private Map<String, Object> isChannelOpen(CreditQualityDto dto,String methodName){
        Map<String, Object> maps = this.loadClientChannelMap(dto);

        if(!maps.containsKey(methodName)){
            throw new BusinessException(ExceptionChannelCode.CHANNEL_NOT_OPEN);
        }
        return maps;
    }


    private void deductionAmount(CreditQualityDto dto,String methodName){
        //server map {key:value} [key:方法名称]， [value：调用次数]; 固定参数：[channel_amount] value:[通道总金额]
        Map<String, Object> maps = this.loadClientChannelMap(dto);

        if(maps.containsKey(methodName)){
            maps.put(methodName,Long.valueOf(maps.get(methodName).toString()) + 1);
        }
        if(maps.containsKey(ClientConstant.CHANNEL_AMOUNT)){
            //TODO 动态修改
            //默认调用金额1.5元
            maps.put(ClientConstant.CHANNEL_AMOUNT,Double.valueOf(maps.get(ClientConstant.CHANNEL_AMOUNT).toString()) - 1.5);
        }

        redisTemplate.put(dto.getClientId(),dto.getServerName(), JsonUtil.toString(maps));
    }


    private Map<String,Object> loadClientChannelMap(CreditQualityDto dto){
        Object object = redisTemplate.getHashKey(dto.getClientId(),dto.getServerName());

        JSONObject jsonObject = JSON.parseObject(object.toString());
        return  JSONObject.toJavaObject(jsonObject, Map.class);
    }
}
