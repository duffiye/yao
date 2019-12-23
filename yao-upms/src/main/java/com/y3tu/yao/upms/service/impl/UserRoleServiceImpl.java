package com.y3tu.yao.upms.service.impl;

import com.github.pagehelper.PageHelper;
import com.y3tu.yao.feign.vo.RoleVO;
import com.y3tu.yao.upms.mapper.RoleMapper;
import com.y3tu.yao.upms.mapper.UserRoleMapper;
import com.y3tu.yao.upms.model.entity.UserRole;
import com.y3tu.yao.upms.model.query.user.UserRoleAddQuery;
import com.y3tu.yao.upms.model.query.user.UserRoleQuery;
import com.y3tu.yao.upms.model.vo.PageVO;
import com.y3tu.yao.upms.model.vo.UserRoleVO;
import com.y3tu.yao.upms.service.UserRoleService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.y3tu.yao.upms.constant.UpmsConstant.ResourceCheck.IS_CHECK;
import static com.y3tu.yao.upms.constant.UpmsConstant.ResourceCheck.NOT_CHECK;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-07
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public List<String> findDepIdsByUserId(String userId) {
        return null;
    }

    @Override
    public RoleVO findRoleVoByUserId(String userId) {
        return roleMapper.selectRoleByUserId(userId);
    }

    @Override
    public PageVO<UserRoleVO> getUserRoleList(UserRoleQuery query) {
        // 查所有角色
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), query.getPageNum() != 0);
        List<UserRoleVO> roleList = roleMapper.selectAllForUser();

        // 查询用户角色
        String uid = query.getUid();
        List<UserRole> userRoleList = userRoleMapper.findByUserId(uid);

        if (userRoleList.isEmpty()) {
            roleList.forEach(role->role.setIsCheck(0));
            return new PageVO<>(roleList);
        }

        // 用户选中了的就设置标签.
        roleList.forEach(role -> userRoleList.forEach(userRole -> {
            if (role.getId().equals(userRole.getRoleId())) {
                role.setIsCheck(IS_CHECK);
            } else {
                role.setIsCheck(NOT_CHECK);
            }
        }));
        return new PageVO<>(roleList);
    }

    @Override
    public void addRef(UserRoleAddQuery query) {
        // 先根据用户查询关联关系,如果关联不存在,则直接新增,如果存在,删除关联关系再新增
        // 查询关联关系
        if (!userRoleMapper.findByUserId(query.getUserID()).isEmpty()) {
            userRoleMapper.deleteByUserId(query.getUserID());
        }
        userRoleMapper.insertRef(query);
    }


}
