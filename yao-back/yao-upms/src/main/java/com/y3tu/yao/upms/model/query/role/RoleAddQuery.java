package com.y3tu.yao.upms.model.query.role;

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
public class RoleAddQuery {

    @NotEmpty(message = "角色名称不能为空")
    private String name;

    @NotEmpty(message = "角色代码不能为空")
    private String code;

    private String remark;

    @JsonProperty("menu_list")
    private List<Integer> menuList;

    @JsonProperty("action_list")
    private List<Integer> actionList;
}
