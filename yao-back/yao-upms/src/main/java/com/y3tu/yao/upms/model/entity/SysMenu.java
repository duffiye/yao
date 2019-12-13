package com.y3tu.yao.upms.model.entity;

import lombok.Data;

/**
 * ClassName: SysMenu
 * Description:
 * date: 2019/11/24 19:30
 *
 * @author zht
 */
@Data
public class SysMenu {


    /**
     * 主键
     */
    private Integer id;

    private Integer types;

    private Integer ranks;

    private Integer systemId;

    private Integer state;

    private String parentId;

    private String name;

    private String code;

    private String icon;

    private String remark;

    private String url;

}
