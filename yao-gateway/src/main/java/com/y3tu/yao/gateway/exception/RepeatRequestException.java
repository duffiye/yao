package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;

/**
 * 没有权限异常
 */
public class RepeatRequestException extends BaseException {
    public RepeatRequestException() {
    }

    public RepeatRequestException(String message) {
        super(message);
    }

    public RepeatRequestException(Throwable e) {
        super(e);
    }

    public RepeatRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatRequestException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public RepeatRequestException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public RepeatRequestException(IError error) {
        super(error);
    }

    public RepeatRequestException(String message, ErrorEnum error) {
        super(message, error);
    }

    public RepeatRequestException(String message, Throwable cause, ErrorEnum error) {
        super(message, cause, error);
    }

    public RepeatRequestException(Throwable cause, ErrorEnum error) {
        super(cause, error);
    }
}
