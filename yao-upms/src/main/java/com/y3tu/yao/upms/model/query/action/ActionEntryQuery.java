package com.y3tu.yao.upms.model.query.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.y3tu.yao.upms.model.query.PageQuery;
import lombok.Data;

/**
 * ClassName: ActionEntryQuery
 * Description:
 * date: 2019/11/25 15:14
 *
 * @author zht
 */
@Data
public class ActionEntryQuery extends PageQuery {

    private String describe;


    private String action;


    @JsonProperty("menu_name")
    private String menuName;
}
