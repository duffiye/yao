package com.y3tu.yao.upms.model.query.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * ClassName: RoleAddQuery
 * Description:
 * date: 2019/11/24 16:46
 *
 * @author zht
 */
@Data
public class RoleUpdateQuery {

    private Integer id;

    private String name;

    private String remark;

    @JsonProperty("menu_list")
    private List<Integer> menuList;

    @JsonProperty("action_list")
    private List<Integer> actionList;
}
