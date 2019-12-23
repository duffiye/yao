package com.y3tu.yao.upms.service;

import com.y3tu.yao.feign.vo.RoleVO;
import com.y3tu.yao.upms.model.entity.Role;
import com.y3tu.yao.upms.model.entity.UserRole;
import com.y3tu.tool.web.base.service.BaseService;
import com.y3tu.yao.upms.model.query.user.UserRoleAddQuery;
import com.y3tu.yao.upms.model.query.user.UserRoleQuery;
import com.y3tu.yao.upms.model.vo.PageVO;
import com.y3tu.yao.upms.model.vo.UserRoleVO;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author y3tu
 */
public interface UserRoleService extends BaseService<UserRole> {

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
     * @param id
     * @return
     */
    RoleVO findRoleVoByUserId(String id);

    /**
     * 功能描述 :
     *
     * @param query
     * @return com.y3tu.yao.upms.model.vo.UserRoleVO
     * @author zht
     * @date 2019/11/25
     */
    PageVO<UserRoleVO> getUserRoleList(UserRoleQuery query);


    /**
     * 功能描述 :添加用户角色关联关系
     *
     * @param query
     * @return void
     * @author zht
     * @date 2019/11/25
     */
    void addRef(UserRoleAddQuery query);
}
