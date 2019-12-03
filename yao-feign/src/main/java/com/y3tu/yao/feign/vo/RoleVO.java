package com.y3tu.yao.feign.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
    @JsonIgnore
    private Integer id;

    private String name;

    private String code;

    private String remark;

    @JsonProperty("menus")
    List<MenuVO> menus;


}
