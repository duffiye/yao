package com.y3tu.yao.upms.model.query.role;

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
public class RoleQuery extends PageQuery {

    @JsonProperty("role_name")
    private String roleName;

}
