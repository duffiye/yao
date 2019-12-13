package com.y3tu.yao.upms.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ClassName: ActionEntryVO
 * Description:
 * date: 2019/11/25 13:39
 *
 * @author zht
 */
@Data
public class ActionEntryQueryVO {

    private Integer id;

    private String action;

    /**
     * 名称/描述
     */
    private String describe;

    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * 菜单名称
     */
    private String menuName;
}
