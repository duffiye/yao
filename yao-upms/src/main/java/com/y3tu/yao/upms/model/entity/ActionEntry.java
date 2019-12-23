package com.y3tu.yao.upms.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ClassName: ActionEntry
 * Description:
 * date: 2019/11/24 18:48
 *
 * @author zht
 */
@Data
public class ActionEntry {

    /**
     * 主键
     */
    private Integer id;


    @JsonProperty("menu_id")
    private Integer menuId;

    /**
     * 操作Code
     */
    private String action;

    /**
     * 描述
     */
    private String describe;


}
