package com.y3tu.yao.upms.service;


import com.y3tu.yao.feign.vo.MenuVO;
import com.y3tu.yao.upms.model.query.menu.MenuAddQuery;
import com.y3tu.yao.upms.model.query.menu.MenuQuery;
import com.y3tu.yao.upms.model.query.menu.MenuUpdateQuery;
import com.y3tu.yao.upms.model.vo.MenuQueryVO;
import com.y3tu.yao.upms.model.vo.PageVO;

import java.util.List;

public interface MenuService {



    /**
     * 功能描述 :根据条件分页查询信息
     *
     * @param query
     * @return com.y3tu.yao.upms.model.vo.PageVO<com.y3tu.yao.upms.model.vo.RoleQueryVO>
     * @author zht
     * @date 2019/11/24
     */
    PageVO<MenuQueryVO> page(MenuQuery query);

    /**
     * 功能描述 :添加菜单信息
     *
     * @param query
     * @return void
     * @author zht
     * @date 2019/11/24
     */
    void add(MenuAddQuery query);

    /**
     * 功能描述 :修改菜单信息
     *
     * @param query
     * @return void
     * @author zht
     * @date 2019/11/24
     */
    void update(MenuUpdateQuery query);


    /**
     * 功能描述 :删除
     *
     * @param roleId
     * @return void
     * @author zht
     * @date 2019/11/24
     */
    void delete(Integer roleId);


    /**
     * 功能描述 :通过菜单code获取菜单列表
     *
     * @param roleCode
     * @return java.util.List<com.y3tu.yao.common.vo.sys.MenuVO>
     * @author zht
     * @date 2019/11/22
     */
    List<MenuVO> findMenuByRoleCode(String roleCode);

    /**
     * 功能描述 :查询所有菜单
     *
     * @return java.util.List<com.y3tu.yao.common.vo.sys.MenuVO>
     * @author zht
     * @date 2019/11/22
     */
    List<MenuVO> list();
}
