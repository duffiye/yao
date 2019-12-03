package com.y3tu.yao.upms.mapper;

import com.y3tu.tool.web.base.mapper.BaseMapper;
import com.y3tu.yao.feign.vo.UserVO;
import com.y3tu.yao.upms.model.entity.User;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author y3tu
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> selectAll();

    /**
     * 通过用户id查询用户信息（含有角色信息）
     *
     * @param userId 用户名
     * @return userVo
     */
    UserVO selectUserVoById(String userId);

    /**
     * 通过用户名查询用户信息（含有角色信息）
     *
     * @param username 用户名
     * @return userVo
     */
    UserVO selectUserVoByUsername(String username);

    /**
     * 通过手机号查询用户信息（含有角色信息）
     *
     * @param mobile 用户名
     * @return userVo
     */
    UserVO selectUserVoByMobile(String mobile);

    /**
     * 通过openId查询用户信息
     *
     * @param openId openid
     * @return userVo
     */
    UserVO selectUserVoByOpenId(String openId);

}