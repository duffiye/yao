package com.y3tu.yao.upms.model.query.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * ClassName: RoleAddQuery
 * Description:
 * date: 2019/11/24 16:46
 *
 * @author zht
 */
@Data
public class MenuUpdateQuery {
    @NotNull(message = "id 不能为空")
    private Integer id;

    @JsonProperty("system_id")
    private String systemId;
    private String code;
    private String name;
    private String icon;
    private String url;
    @JsonProperty("parent_id")
    private String parentId;
    /**
     * 菜单排序
     */
    private String ranks;
}
