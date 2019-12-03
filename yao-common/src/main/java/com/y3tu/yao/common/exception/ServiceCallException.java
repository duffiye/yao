package com.y3tu.yao.common.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;

/**
 * 服务调用异常
 *
 * @author zouht
 * @date 2019年11月30日
 */
public class ServiceCallException extends BaseException {
    public ServiceCallException() {
    }

    public ServiceCallException(String message) {
        super(message);
    }

    public ServiceCallException(Throwable e) {
        super(e);
    }

    public ServiceCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceCallException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public ServiceCallException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public ServiceCallException(IError error) {
        super(error);
    }

    public ServiceCallException(String message, ErrorEnum error) {
        super(message, error);
    }

    public ServiceCallException(String message, Throwable cause, ErrorEnum error) {
        super(message, cause, error);
    }

    public ServiceCallException(Throwable cause, ErrorEnum error) {
        super(cause, error);
    }
}
