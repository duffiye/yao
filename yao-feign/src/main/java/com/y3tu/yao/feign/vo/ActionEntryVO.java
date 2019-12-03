package com.y3tu.yao.feign.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 页面、按钮资源权限
 */
@Data
@Accessors(chain = true)
public class ActionEntryVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;


    @JsonIgnore
    private Integer menuId;

    /**
     * 操作Code
     */
    private String action;

    /**
     * 描述
     */
    private String describe;


    /**
     * 是否被选择,0:没有,1,被选中
     */
    @JsonProperty("is_check")
    private Integer isCheck;

}
