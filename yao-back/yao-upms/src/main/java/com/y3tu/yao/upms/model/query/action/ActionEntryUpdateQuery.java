package com.y3tu.yao.upms.model.query.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * ClassName: ActionEntryAddQuery
 * Description:
 * date: 2019/11/25 15:31
 *
 * @author zht
 */
@Data
public class ActionEntryUpdateQuery {
    @NotNull(message = "id不能为空")
    private Integer id;
    /**
     * 名称
     */
    private String describe;


    /**
     * 动作
     */
    private String action;

    /**
     * 菜单ID
     */
    @JsonProperty("menu_id")
    private String menuId;
}
