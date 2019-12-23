package com.y3tu.yao.upms.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.y3tu.yao.feign.vo.MenuVO;
import com.y3tu.yao.upms.model.entity.SysMenu;
import com.y3tu.yao.upms.model.query.menu.MenuQuery;
import com.y3tu.yao.upms.model.vo.MenuQueryVO;

import java.util.List;

/**
 * 资源Mapper
 *
 * @author y3tu
 */
public interface MenuMapper extends BaseMapper<SysMenu> {
    /**
     * id
     *
     * @param roleId
     * @return
     */
    List<MenuVO> findMenuByRoleId(Integer roleId);

    /**
     * 根据角色code查询菜单
     *
     * @param code
     * @return
     */
    List<MenuVO> findMenuByRoleCode(String code);

    /**
     * 功能描述 :查询所有菜单
     *
     * @return java.util.List<com.y3tu.yao.common.vo.sys.MenuVO>
     * @author zht
     * @date 2019/11/22
     */
    List<MenuVO> selectAll();

    /**
     * 功能描述 :根据条件查询菜单列表
     *
     * @param query
     * @return java.util.List<com.y3tu.yao.upms.model.vo.MenuQueryVO>
     * @author zht
     * @date 2019/11/24
     */
    List<MenuQueryVO> selectByConditions(MenuQuery query);
}