package com.y3tu.yao.upms.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ClassName: UserRoleVO
 * Description:
 * date: 2019/11/25 17:33
 *
 * @author zht
 */
@Data
public class UserRoleVO {
    private Integer id;

    private String code;

    private String name;

    @JsonProperty("is_check")
    private Integer isCheck;

}
