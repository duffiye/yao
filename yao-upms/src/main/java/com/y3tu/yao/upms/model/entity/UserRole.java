package com.y3tu.yao.upms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 用户角色关联表
 * </p>
 *
 * @author y3tu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity<UserRole> {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deletedAt;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 用户ID
     */
    private String userId;

}
