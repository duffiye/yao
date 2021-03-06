package com.y3tu.yao.common.vo.sys;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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


    private String name;

    private Integer state;

    private String remark;

    private String code;

    @JsonProperty("menus")
    List<MenuVO> menus;





}
