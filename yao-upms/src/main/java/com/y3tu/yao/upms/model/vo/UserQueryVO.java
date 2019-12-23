package com.y3tu.yao.upms.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: UserVo
 * Description:
 * date: 2019/11/22 18:12
 *
 * @author zht
 */
@Data
public class UserQueryVO {
    private Integer id;

    private String uid;

    @JsonProperty(value = "user_name")
    private String username;

    @JsonProperty(value = "full_name")
    private String fullName;

    private String phone;

    @JsonProperty(value = "role_name")
    private String roleName;

    @JsonProperty(value = "created_at")
    private String createdAt;

    /**
     * 最后登录时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "last_login_at")
    private Date lastLoginAt;

    /**
     * 最后登录IP
     */
    @JsonProperty(value = "last_login_ip_at")
    private String lastLoginIpAt;
    /**
     * 状态
     */
    private Integer state;

}
