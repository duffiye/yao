package com.y3tu.yao.upms.model.query.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.y3tu.yao.upms.model.query.PageQuery;
import lombok.Data;

/**
 * ClassName: RoleQueryQuery
 * Description:
 * date: 2019/11/24 16:15
 *
 * @author zht
 */
@Data
public class MenuQuery extends PageQuery {

    @JsonProperty("menu_name")
    private String menuName;

}
