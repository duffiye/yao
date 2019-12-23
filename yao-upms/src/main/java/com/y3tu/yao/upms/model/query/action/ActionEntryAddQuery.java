package com.y3tu.yao.upms.model.query.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * ClassName: ActionEntryAddQuery
 * Description:
 * date: 2019/11/25 15:31
 *
 * @author zht
 */
@Data
public class ActionEntryAddQuery {
    /**
     * 名称
     */
    @NotEmpty(message = "名称不能为空")
    private String describe;

    /**
     * 动作
     */
    @NotEmpty(message = "动作不能为空")
    private String action;

    /**
     * 菜单ID
     */
    @JsonProperty("menu_id")
    @NotEmpty(message = "菜单ID不能为空")
    private String menuId;
}
