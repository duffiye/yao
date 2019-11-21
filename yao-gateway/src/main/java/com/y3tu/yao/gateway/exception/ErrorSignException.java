package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;

/**
 * 请求头信息错误异常
 */
public class ErrorSignException extends BaseException {
    public ErrorSignException() {
    }

    public ErrorSignException(String message) {
        super(message);
    }

    public ErrorSignException(Throwable e) {
        super(e);
    }

    public ErrorSignException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorSignException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public ErrorSignException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public ErrorSignException(IError error) {
        super(error);
    }

    public ErrorSignException(String message, ErrorEnum error) {
        super(message, error);
    }

    public ErrorSignException(String message, Throwable cause, ErrorEnum error) {
        super(message, cause, error);
    }

    public ErrorSignException(Throwable cause, ErrorEnum error) {
        super(cause, error);
    }
}
