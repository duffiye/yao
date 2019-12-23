package com.y3tu.yao.upms.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: RoleQueryVo
 * Description:
 * date: 2019/11/24 16:09
 *
 * @author zht
 */
@Data
public class RoleQueryVO {

    private Integer id;

    @JsonProperty("role_name")
    private String roleName;


    private String remark;

    @JsonProperty("created_at")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}
