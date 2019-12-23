package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;
import com.y3tu.tool.core.exception.SystemException;

/**
 *功能描述 :请求时间在限值之内
 * @author zht
 * @date 2019/12/23
 */
public class TimeLimitException extends SystemException {
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
