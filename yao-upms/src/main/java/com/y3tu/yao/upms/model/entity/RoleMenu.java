package com.y3tu.yao.upms.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * ClassName: RoleMenu
 * Description:
 * date: 2019/11/24 17:01
 *
 * @author zht
 */
@Data
public class RoleMenu {
    private Integer id;
    private Integer roleId;
    private Integer menuId;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
