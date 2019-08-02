package com.y3tu.yao.log.server.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * 用户操作日志表
 *
 * @author y3tu
 */
@Data
@Accessors(chain = true)
@TableName("t_log")
public class Log extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 模块名
     */
    @TableField("module_name")
    private String moduleName;

    /**
     * 操作名
     */
    @TableField("action_name")
    private String actionName;
    /**
     * 操作类型
     */
    @TableField("action_type")
    private String actionType;
    /**
     * 服务名
     */
    @TableField("server_name")
    private String serverName;

    /**
     * 操作IP地址
     */
    @TableField("remote_addr")
    private String remoteAddr;

    /**
     * 用户客户端信息
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 请求URI
     */
    @TableField("request_uri")
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
    @TableField("create_time")
    private Date createTime;
    /**
     * 操作状态 1 失败  0 成功
     */
    private String status;

}