package com.y3tu.yao.common.vo.sys;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResourceVO {


    String username;

    String avatar;

    int state;

    @JsonProperty("last_login_ip")
    String lastLoginIP;

    @JsonProperty("last_login_at")
    String lastLoginAt;

    @JsonProperty("role")
    RoleVO roleVO;
}
