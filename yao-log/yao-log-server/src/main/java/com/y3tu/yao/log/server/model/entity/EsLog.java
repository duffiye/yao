package com.y3tu.yao.log.server.model.entity;

import com.y3tu.tool.core.util.IdUtil;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.Date;


/**
 * Elasticsearch文档实体类
 *
 * @author y3tu
 */
@Data
@Document(indexName = "log", type = "log", shards = 1, replicas = 0, refreshInterval = "-1")
public class EsLog implements Serializable {

    private String id = String.valueOf(IdUtil.createSnowflake(1, 1).nextId());

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
     * 服务名
     */
    private String serverName;

    /**
     * 操作IP地址
     */
    private String remoteAddr;

    /**
     * 用户代理
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
