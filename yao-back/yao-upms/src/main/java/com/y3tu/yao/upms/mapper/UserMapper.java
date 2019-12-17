package com.y3tu.yao.upms.mapper;

import com.y3tu.tool.web.base.mapper.BaseMapper;
import com.y3tu.yao.feign.vo.UserVO;
import com.y3tu.yao.upms.model.entity.User;
import com.y3tu.yao.upms.model.query.user.UserQuery;
import com.y3tu.yao.upms.model.vo.UserQueryVO;

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

    /**
     * 功能描述 :查自己
     *
     * @param userId
     * @return java.util.List<com.y3tu.yao.upms.model.vo.UserQueryVO>
     * @author zht
     * @date 2019/11/22
     */
    List<UserQueryVO> selectSelf(Integer userId);

    /**
     * 功能描述 :根据条件查询用户列表
     *
     * @param query
     * @return java.util.List<com.y3tu.yao.upms.model.vo.UserQueryVO>
     * @author zht
     * @date 2019/11/22
     */
    List<UserQueryVO> selectByCondition(UserQuery query);


    /**
     * 功能描述 : 根据uid 查询用户信息
     *
     * @param uid
     * @return com.xft.upms.model.entity.User
     * @author zht
     * @date 2019/11/23
     */
    User selectUserByUid(String uid);

    /**
     * 功能描述 :根据uid 查询用户信息
     *
     * @param id id
     * @return
     * @author zht
     * @date 2019/12/3
     */
    User selectByUserID(Integer id);


}