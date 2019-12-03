package com.y3tu.yao.feign.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 */
@Data
@Accessors(chain = true)
public class MenuVO implements Serializable {

    @JsonProperty("system_id")
    private Integer systemId;

    /**
     * 主键
     */
    private Integer id;

    private Integer types;

    @JsonIgnore
    private Integer ranks;

    @JsonIgnore
    private Integer state;

    @JsonProperty("parent_id")
    private String parentId;

    private String name;

    private String code;

    private String icon;

    private String remark;

    private String url;

    @JsonProperty(value = "is_check", defaultValue = "0")
    private Integer isCheck;

    protected List<MenuVO> children;

    @JsonProperty("action_entry_set")
    private List<ActionEntryVO> actionEntrySet;

    public void add(MenuVO menuVO) {
        children.add(menuVO);
    }
}
