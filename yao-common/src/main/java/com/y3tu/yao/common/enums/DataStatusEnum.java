package com.y3tu.yao.common.enums;

/**
 * 功能描述 :数据状态枚举
 *
 * @param
 * @author zht
 * @date 2019/12/23
 * @return
 */
public enum DataStatusEnum {

    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 删除
     */
    DELETE(1, "删除");


    private int code;

    private String message;

    DataStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
