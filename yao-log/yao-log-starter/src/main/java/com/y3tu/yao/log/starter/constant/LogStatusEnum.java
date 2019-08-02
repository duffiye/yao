package com.y3tu.yao.log.starter.constant;

/**
 * 用户操作日志状态枚举
 *
 * @author y3tu
 * @date 2019-08-02
 */
public enum LogStatusEnum {
    /**
     * 成功
     */
    SUCCESS("0", "成功"),
    /**
     * 失败
     */
    FAIL("1", "失败");


    private String code;

    private String message;

    LogStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
