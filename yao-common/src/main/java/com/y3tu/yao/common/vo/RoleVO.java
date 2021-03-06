package com.y3tu.yao.common.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 */
@Data
@Accessors(chain = true)
public class RoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 删除时间
     */
    private Date deletedAt;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer state;


    /**
     * 角色编码
     */
    private String code;

    /**
     * 备注
     */
    private String remark;


}
