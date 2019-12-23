package com.y3tu.yao.upms.model.query.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.y3tu.yao.upms.model.query.PageQuery;
import lombok.Data;

/**
 * ClassName: UserQueryRequest
 * Description:
 * date: 2019/11/22 18:04
 *
 * @author zht
 */
@Data
public class UserQuery extends PageQuery {

    private String username;

    @JsonProperty(value = "full_name")
    private String fullName;

    private String phone;

    private Integer userId;
}
