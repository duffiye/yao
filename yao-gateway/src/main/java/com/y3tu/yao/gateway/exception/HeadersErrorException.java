package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;

/**
 * 请求头信息错误异常
 */
public class HeadersErrorException extends BaseException {
    public HeadersErrorException() {
    }

    public HeadersErrorException(String message) {
        super(message);
    }

    public HeadersErrorException(Throwable e) {
        super(e);
    }

    public HeadersErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public HeadersErrorException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public HeadersErrorException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public HeadersErrorException(IError error) {
        super(error);
    }

    public HeadersErrorException(String message, ErrorEnum error) {
        super(message, error);
    }

    public HeadersErrorException(String message, Throwable cause, ErrorEnum error) {
        super(message, cause, error);
    }

    public HeadersErrorException(Throwable cause, ErrorEnum error) {
        super(cause, error);
    }
}
