package com.y3tu.yao.upms.model.query.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * ClassName: RoleAddQuery
 * Description:
 * date: 2019/11/24 16:46
 *
 * @author zht
 */
@Data
public class MenuAddQuery {

    @NotEmpty(message = "菜单类别不能为空")
    @JsonProperty("system_id")
    private String systemId;

    @NotEmpty(message = "菜单编码不能为空")
    private String code;

    @NotEmpty(message = "菜单名称不能为空")
    private String name;

    private String icon;

    private String url;

    @JsonProperty("parent_id")
    private String parentId;
    /**
     * 菜单排序
     */
    private String ranks;
    /**
     * 角色id列表
     */
    @JsonProperty("role_id_list")
    private List<Integer> roleIdList;
}
