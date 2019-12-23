package com.y3tu.yao.common.enums;

/**
 * 功能描述 :资源类型枚举
 *
 * @param
 * @author zht
 * @date 2019/12/23
 * @return
 */
public enum ResourceTypeEnum {

    /**
     * 顶层菜单
     */
    TOP_MENU(-1, "顶层菜单"),

    /**
     * 普通菜单
     */
    MENU(0, "普通菜单"),

    /**
     * 按钮
     */
    BUTTON(1, "按钮");


    private int code;

    private String message;

    ResourceTypeEnum(int code, String message) {
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
