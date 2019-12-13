package com.y3tu.yao.upms.mapper;

import com.y3tu.tool.web.base.mapper.BaseMapper;
import com.y3tu.yao.upms.model.entity.UserRole;
import com.y3tu.yao.upms.model.query.user.UserRoleAddQuery;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author y3tu
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 根据用户Id获取用户所属角色
     *
     * @param uid
     * @return
     */
    List<UserRole> findByUserId(Integer uid);

    /**
     * 根据用户Id获取用户所属部门
     *
     * @param userId
     * @return
     */
    List<String> findDepIdsByUserId(String userId);

    /**
     * 根据用户Id获取用户所属角色
     *
     * @param uid
     * @return
     */
    List<UserRole> findByUserId(String uid);

    /**
     * 功能描述 :根据角色ID查找关联关系
     *
     * @param roleId
     * @return java.util.List<com.xft.upms.model.entity.Role>
     * @author zht
     * @date 2019/11/24
     */
    List<UserRole> findByRoleId(Integer roleId);

    /**
     * 功能描述 :新增
     *
     * @param query
     * @return void
     * @author zht
     * @date 2019/11/25
     */
    void insertRef(UserRoleAddQuery query);

    /**
     * 功能描述 : 删除
     *
     * @param uid
     * @return void
     * @author zht
     * @date 2019/11/25
     */
    void deleteByUserId(String uid);
}