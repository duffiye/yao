package com.y3tu.yao.log.starter.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 日志表
 *
 * @author y3tu
 */
@Data
@Accessors(chain = true)
public class LogDTO implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 操作名
     */
    private String actionName;
    /**
     * 操作类型
     */
    private String actionType;
    /**
     * 服务ID
     */
    private String serverName;

    /**
     * 操作IP地址
     */
    private String remoteAddr;

    /**
     * 用户调用客户端信息
     */
    private String userAgent;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作方式
     */
    private String method;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 执行时间
     */
    private String time;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 调用者
     */
    private String username;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作状态 1 失败  0 成功
     */
    private String status;

}