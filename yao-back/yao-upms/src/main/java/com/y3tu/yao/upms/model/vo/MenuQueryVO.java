package com.y3tu.yao.upms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ClassName: MenuQueryVO
 * Description:
 * date: 2019/11/24 19:20
 *
 * @author zht
 */
@Data
public class MenuQueryVO {
    @JsonProperty("system_id")
    private Integer systemId;

    /**
     * 主键
     */
    private Integer id;

    private Integer types;

    private Integer ranks;

    @JsonIgnore
    private Integer state;

    @JsonProperty("parent_id")
    private String parentId;

    @JsonProperty("parent_name")
    private String parentName;

    private String name;

    private String code;

    private String icon;

    private String remark;

    private String url;


}
