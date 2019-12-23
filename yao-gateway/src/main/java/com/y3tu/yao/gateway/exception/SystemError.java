package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.IError;

/**
 * ClassName: BusinessError
 * Description:
 * date: 2019/12/17 11:15
 *
 * @author zht
 */
public enum SystemError implements IError {
    /**请求头部信息异常*/
    HEADERS_ERROR("headers-error", "请求头部信息异常"),
    /**访问未授权*/
    NO_PERMISSION("no-permission", "访问未授权"),
    /**重复的请求*/
    REPEAT_REQUEST("repeat-request", "重复的请求"),
    /**请求时间异常*/
    TIME_ERROR("time-error", "请求时间异常"),
    /**token错误或者token过期*/
    UNAUTHORIZED("unauthorized", "token错误或者token过期"),
    /**错误的签名信息*/
    SIGN_ERROR("error-sign", "错误的签名信息"),
    /**错误的请求方式*/
    HTTP_METHOD_ERROR("error-http-method", "错误的请求方式"),
    /**系统内部错误*/
    OTHER_ERROR("other-error", "系统内部错误");

    String code;
    String message;

    SystemError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
