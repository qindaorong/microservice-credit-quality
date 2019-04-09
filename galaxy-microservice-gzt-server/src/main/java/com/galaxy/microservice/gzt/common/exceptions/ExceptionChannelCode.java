package com.galaxy.microservice.gzt.common.exceptions;

import com.galaxy.framework.entity.CodeMessage;

/**
 * 应用异常编码
 *
 * @author
 * @since
 */
public class ExceptionChannelCode {

    /**
     * 服务器异常
     */
    public static final CodeMessage SERVICE_BUSY = new CodeMessage(000000, "服务器繁忙，请稍后重试");

    /**
     * 服务器参数异常
     */
    public static final CodeMessage PARAMETER_MISMATCH = new CodeMessage(100000, "方法参数不要匹配");

    /**
     * 该通道没有开启
     */
    public static final CodeMessage CHANNEL_NOT_OPEN = new CodeMessage(200001, "该通道没有开启");

    /**
     * 账户余额不足
     */
    public static final CodeMessage BALANCE_ENOUGH = new CodeMessage(200002, "账户余额不足");


}