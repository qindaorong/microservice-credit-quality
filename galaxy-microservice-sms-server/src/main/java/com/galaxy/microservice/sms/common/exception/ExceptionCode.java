package com.galaxy.microservice.sms.common.exception;

import com.galaxy.framework.entity.CodeMessage;

/**
 * 应用异常编码
 *
 * @author
 * @since
 */
public class ExceptionCode {

    /**
     * 服务器异常
     */
    public static final CodeMessage SERVICE_BUSY = new CodeMessage(000000, "服务器繁忙，请稍后重试");

    /**
     * 系统异常
     */
    public static final CodeMessage LOW_VERSION = new CodeMessage(010000, "版本过低，当前功能不支持");

    /**
     * 接口调用异常
     */
    public static final CodeMessage INTERFACE_USE_FAILURE = new CodeMessage(100000, "接口调用失败");




    /**
     * 成功
     */
    public static final CodeMessage OK = new CodeMessage(200, "成功");

    /**
     * 未授权
     */
    public static final CodeMessage UNAUTHORIZED = new CodeMessage(401, "未授权");


    /**
     * 当前用户无权限
     */
    public static final CodeMessage FORBIDDEN = new CodeMessage(403, "当前用户无权限");

    /**
     * 当前用户无权限
     */
    public static final CodeMessage SERVICE_UNAVAILABL = new CodeMessage(503, "服务不可用");

    /**
     * 调用SMS service 接口错误
     */
    public static final CodeMessage SMS_SERVICE_ERROR = new CodeMessage(6001, "调用短信提供方接口错误");

    /**
     * 发送验证码次数上限
     */
    public static final CodeMessage ONE_MIN_SERVICE_ERROR = new CodeMessage(6002, "一分钟内不能重复发送");

    /**
     * 短信验证已过期
     */
    public static final CodeMessage CODE_EXPIRATION = new CodeMessage(6003, "短信验证已过期");


    /**
     * 短信验证码错误
     */
    public static final CodeMessage CODE_ERROR = new CodeMessage(6004, "短信验证码错误");


    /**
     * 调用SMS service 接口错误
     */
    public static final CodeMessage MESSAGE_SERVICE_ERROR = new CodeMessage(6005, "调用短信提供方接口错误");

    /**
     * 发送次数上限
     */
    public static final CodeMessage DAILY_CODE_UPPER_LIMIT = new CodeMessage(6006, "当天发送次数上限");


    /**
     * 请求信息不能为空
     */
    public static final CodeMessage MESSAGE_NOT_NULL = new CodeMessage(6007, "请求信息不能为空");

    /**
     * 用户该通道没有开通
     */
    public static final CodeMessage  CHANNEL_NO_OPEN = new CodeMessage(6009, "用户该通道没有开通");

    /**
     * 有效时间不能为空
     */
    public static final CodeMessage TIME_NOT_NULL = new CodeMessage(6011, "有效时间不能为空");
    /**
     * 验证码不可以为空
     */
    public static final CodeMessage VERIFICATIONCODE_NOT_NULL = new CodeMessage(6012, "验证码不能为空");







}