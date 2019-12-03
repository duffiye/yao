package com.y3tu.yao.feign.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class UserVO {

    private Integer id;
    private String uid;
    private String email;
    private String phone;
    private String username;
    private String password;

    @JsonProperty("created_at")
    private String createdAt;
    /**
     * 上次登录时间
     */
    @JsonProperty("last_login_at")
    private String lastLoginAt;

    @JsonProperty("full_name")
    private String fullName;
    /**
     * 上次登录IP
     */
    @JsonProperty("last_login_ip_at")
    private String lastLoginIpAt;

    @JsonProperty("role_name")
    private String roleName;

    /**
     * 状态
     */
    private Integer state;
    /**
     * 重试次数
     */
    @JsonIgnore
    private Integer retryTimes;

}
