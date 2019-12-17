package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;
import com.y3tu.tool.core.exception.SystemException;

/**
 * 请求头信息错误异常
 */
public class ErrorHTTPMethodException extends SystemException {
    public ErrorHTTPMethodException() {
    }

    public ErrorHTTPMethodException(String message) {
        super(message);
    }

    public ErrorHTTPMethodException(Throwable e) {
        super(e);
    }

    public ErrorHTTPMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorHTTPMethodException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public ErrorHTTPMethodException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public ErrorHTTPMethodException(IError error) {
        super(error);
    }

    public ErrorHTTPMethodException(String message, ErrorEnum error) {
        super(message, error);
    }

    public ErrorHTTPMethodException(String message, Throwable cause, ErrorEnum error) {
        super(message, cause, error);
    }

    public ErrorHTTPMethodException(Throwable cause, ErrorEnum error) {
        super(cause, error);
    }
}
