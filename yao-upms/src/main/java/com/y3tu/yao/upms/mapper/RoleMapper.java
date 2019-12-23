package com.y3tu.yao.upms.mapper;

import com.y3tu.yao.feign.vo.RoleVO;
import com.y3tu.yao.upms.model.entity.Role;
import com.y3tu.tool.web.base.mapper.BaseMapper;
import com.y3tu.yao.upms.model.query.role.RoleQuery;
import com.y3tu.yao.upms.model.vo.RoleQueryVO;
import com.y3tu.yao.upms.model.vo.UserRoleVO;

import java.util.List;

/**
 * 角色Mapper
 *
 * @author y3tu
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 功能描述 :根据user查询role(一个用户只有一个角色)
     *
     * @param userId
     * @return com.y3tu.yao.common.vo.sys.RoleVO
     * @author zht
     * @date 2019/11/21
     */
    RoleVO selectRoleByUserId(String userId);


    /**
     * 功能描述 :根据主键查找角色信息
     *
     * @param id
     * @return com.y3tu.yao.common.vo.sys.RoleVO
     * @author zht
     * @date 2019/11/23
     */
    RoleVO selectByPrimaryKey(Integer id);

    /**
     * 功能描述 :根据条件查询角色列表
     *
     * @param query
     * @return java.util.List<com.y3tu.yao.upms.model.vo.RoleQueryVO>
     * @author zht
     * @date 2019/11/24
     */
    List<RoleQueryVO> selectByCondition(RoleQuery query);


    /**
     * 功能描述 :根据角色名查询角色信息
     *
     * @param name
     * @return java.util.List<com.xft.upms.model.entity.Role>
     * @author zht
     * @date 2019/11/24
     */
    List<Role> selectByName(String name);


    /**
     * 功能描述 :查出所有角色
     *
     * @param
     * @return java.util.List<com.xft.upms.model.entity.Role>
     * @author zht
     * @date 2019/11/25
     */
    List<UserRoleVO> selectAllForUser();


    /**
     *功能描述 :查询角色列表
     * @author zht
     * @date 2019/11/25
     * @param
     * @return java.util.List<com.y3tu.yao.upms.model.vo.RoleQueryVO>
     */
    List<RoleQueryVO> selectAll();

}