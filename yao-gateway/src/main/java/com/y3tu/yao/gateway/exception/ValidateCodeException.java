package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;
import com.y3tu.tool.core.exception.SystemException;

/**
 * 验证码异常
 *
 * @author y3tu
 */
public class ValidateCodeException extends SystemException {
    public ValidateCodeException() {
    }

    public ValidateCodeException(String message) {
        super(message);
    }

    public ValidateCodeException(Throwable e) {
        super(e);
    }

    public ValidateCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateCodeException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public ValidateCodeException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public ValidateCodeException(IError error) {
        super(error);
    }

    public ValidateCodeException(String message, ErrorEnum error) {
        super(message, error);
    }

    public ValidateCodeException(String message, Throwable cause, ErrorEnum error) {
        super(message, cause, error);
    }

    public ValidateCodeException(Throwable cause, ErrorEnum error) {
        super(cause, error);
    }
}
