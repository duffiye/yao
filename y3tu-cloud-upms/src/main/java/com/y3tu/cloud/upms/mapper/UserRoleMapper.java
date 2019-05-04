package com.y3tu.cloud.upms.mapper;

import com.y3tu.cloud.upms.model.entity.Role;
import com.y3tu.cloud.upms.model.entity.UserRole;
import com.y3tu.tool.web.base.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author y3tu
 * @date 2018-08-07
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 根据用户Id获取用户所属角色
     *
     * @param id
     * @return
     */
    List<Role> findByUserId(String id);

    /**
     * 根据用户Id获取用户所属部门
     *
     * @param userId
     * @return
     */
    List<String> findDepIdsByUserId(String userId);
}