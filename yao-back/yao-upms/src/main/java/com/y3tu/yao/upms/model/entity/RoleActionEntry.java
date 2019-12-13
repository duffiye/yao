package com.y3tu.yao.upms.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ClassName: RoleActionEntity
 * Description:
 * date: 2019/11/23 18:29
 *
 * @author zht
 */
@Data
public class RoleActionEntry {
    private Integer id;

    @JsonProperty("role_id")
    private Integer roleId;

    @JsonProperty("action_entry_id")
    private Integer actionEntryId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

}
