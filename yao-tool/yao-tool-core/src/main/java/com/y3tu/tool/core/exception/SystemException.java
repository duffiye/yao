package com.y3tu.tool.core.exception;

/**
 * 业务异常
 *
 * @author y3tu
 * @date 2018/10/2
 */
public class SystemException extends BaseException {
    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable e) {
        super(e);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public SystemException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public SystemException(IError error) {
        super(error);
    }

    public SystemException(String message, IError error) {
        super(message, error);
    }

    public SystemException(String message, Throwable cause, IError error) {
        super(message, cause, error);
    }

    public SystemException(Throwable cause, IError error) {
        super(cause, error);
    }

}
