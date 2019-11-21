package com.y3tu.yao.common.vo.sys;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 */
@Data
@Accessors(chain = true)
public class MenuVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    private Integer types;

    private Integer ranks;

    private Integer state;

    private Integer parentID;

    private String title;

    private String name;

    private String key;

    private String component;

    private String icon;

    private List<ActionEntryVO> actionEntrySet;
}
