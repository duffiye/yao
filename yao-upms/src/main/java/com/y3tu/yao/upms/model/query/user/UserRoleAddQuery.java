package com.y3tu.yao.upms.model.query.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ClassName: UserRoleQuery
 * Description:
 * date: 2019/11/25 17:58
 *
 * @author zht
 */
@Data
public class UserRoleAddQuery {

    @JsonProperty("user_id")
    private String userID;

    @JsonProperty("role_id")
    private Integer roleID;
}
