package com.y3tu.yao.common.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class MenuPO implements Serializable {

    private String id;

    private String name;

    private String key;

    private String component;

    private String icon;

    private List<MenuPO> children;


}
