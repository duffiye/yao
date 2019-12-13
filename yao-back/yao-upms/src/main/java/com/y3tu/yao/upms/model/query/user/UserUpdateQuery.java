package com.y3tu.yao.upms.model.query.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * ClassName: UserUpdateQuery
 * Description:
 * date: 2019/11/23 0:34
 *
 * @author zht
 */
@Data
public class UserUpdateQuery {


    @NotEmpty(message = "uid不能为空")
    private String uid;

    /**
     * 手机
     */
    private String phone;

    /**
     * 姓名
     */
    @JsonProperty(value = "full_name")
    private String fullName;

}
