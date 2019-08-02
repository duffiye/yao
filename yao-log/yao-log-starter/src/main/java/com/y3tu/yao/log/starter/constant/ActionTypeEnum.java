package com.y3tu.yao.log.starter.constant;

/**
 * 操作类型枚举
 *
 * @author y3tu
 * @date 2019-08-02
 */
public enum ActionTypeEnum {
    /**
     * 登录
     */
    LOGIN("LOGIN"),
    /**
     * 登出
     */
    LOGOUT("LOGOUT"),
    /**
     * 查看
     */
    VIEW("VIEW"),
    /**
     * 新增
     */
    ADD("ADD"),
    /**
     * 编辑
     */
    EDIT("EDIT"),
    /**
     * 删除
     */
    DELETE("DELETE"),
    /**
     * 上传
     */
    UPLOAD("UPLOAD"),
    /**
     * 下载
     */
    DOWNLOAD("DOWNLOAD"),
    /**
     * 调用后台程序
     */
    CALL("CALL"),
    /**
     * 其他
     */
    OTHER("OTHER");

    private String value;

    ActionTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
