package com.y3tu.yao.upms.model.query.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * ClassName: UserAddQuery
 * Description:
 * date: 2019/11/22 23:20
 *
 * @author zht
 */
@Data
public class UserAddQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机
     */
    @NotEmpty(message = "手机号码不能为空")
    private String phone;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @JsonProperty(value = "full_name")
    private String fullName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
