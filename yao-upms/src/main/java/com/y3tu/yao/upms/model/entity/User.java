package com.y3tu.yao.upms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author y3tu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField("created_at")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @TableField("updated_at")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @TableField("deleted_at")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;

    /**
     * uid
     */
    private Integer uid;


    /**
     * 手机
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

    @JsonProperty(value = "full_name")
    private String fullName;

    /**
     * 密码
     */
    private String password;

    /**
     * 最后登录时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginAt;

    /**
     * 最后登录IP
     */
    private String lastLoginIpAt;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 角色代码
     */
    private String roleCode;


}
