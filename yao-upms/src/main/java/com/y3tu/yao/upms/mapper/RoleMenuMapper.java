package com.y3tu.yao.upms.mapper;

import com.y3tu.yao.upms.model.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: Role
 * Description:
 * date: 2019/11/24 17:33
 *
 * @author zht
 */
public interface RoleMenuMapper {

    /**
     * 功能描述 :批量插入
     *
     * @param list list
     * @return void
     * @author zht
     * @date 2019/11/24
     */
    void batchInsert(@Param("list") List<RoleMenu> list);

    /**
     * 功能描述 : 根据roleId 删除关联记录
     *
     * @param roleId 角色id
     * @return void
     * @author zht
     * @date 2019/11/24
     */
    void deleteByRoleId(Integer roleId);

    /**
     * 功能描述 :根据菜单ID查询列表
     *
     * @param menuId 菜单id
     * @return java.util.List<com.xft.upms.model.entity.RoleMenu>
     * @author zht
     * @date 2019/11/24
     */
    List<RoleMenu> selectByMenuId(Integer menuId);
}
