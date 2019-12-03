package com.y3tu.yao.feign.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResourceVO {

    private String username;

    @JsonProperty(value = "full_name")
    private String fullName;

    private Integer state;

    @JsonProperty("role")
    private RoleVO roleVO;
}
