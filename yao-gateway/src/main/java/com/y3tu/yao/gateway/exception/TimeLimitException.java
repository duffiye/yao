package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;

/**
 * 请求头信息错误异常
 */
public class TimeLimitException extends BaseException {
    public TimeLimitException() {
    }

    public TimeLimitException(String message) {
        super(message);
    }

    public TimeLimitException(Throwable e) {
        super(e);
    }

    public TimeLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeLimitException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public TimeLimitException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public TimeLimitException(IError error) {
        super(error);
    }

    public TimeLimitException(String message, ErrorEnum error) {
        super(message, error);
    }

    public TimeLimitException(String message, Throwable cause, ErrorEnum error) {
        super(message, cause, error);
    }

    public TimeLimitException(Throwable cause, ErrorEnum error) {
        super(cause, error);
    }
}
