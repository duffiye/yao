package com.y3tu.yao.common.vo.sys;

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

    /**
     * 操作Code
     */
    private String action;

    /**
     * 描述
     */
    private String describe;
}
