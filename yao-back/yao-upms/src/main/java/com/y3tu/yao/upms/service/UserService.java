package com.y3tu.yao.upms.service;

import com.y3tu.tool.web.base.service.BaseService;
import com.y3tu.yao.feign.vo.UserVO;
import com.y3tu.yao.upms.model.dto.UserDTO;
import com.y3tu.yao.upms.model.entity.User;
import com.y3tu.yao.upms.model.query.user.UserAddQuery;
import com.y3tu.yao.upms.model.query.user.UserQuery;
import com.y3tu.yao.upms.model.query.user.UserUpdateQuery;
import com.y3tu.yao.upms.model.vo.PageVO;
import com.y3tu.yao.upms.model.vo.UserQueryVO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author y3tu
 */
public interface UserService extends BaseService<User> {

    /**
     * 查询全量用户信息
     *
     * @return
     */
    List<UserDTO> selectAll();

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户名
     * @return userVo
     */
    UserVO findUserById(String userId);

    /**
     * 根据用户名查询用户角色信息
     *
     * @param username 用户名
     * @return userVo
     */
    UserVO findUserByUsername(String username);

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    UserVO findUserByMobile(String mobile);

    /**
     * 通过openId查询用户
     *
     * @param openId openId
     * @return 用户信息
     */
    UserVO findUserByOpenId(String openId);

    /**
     * 功能描述 :根据条件分页查询用户信息
     *
     * @param userQuery
     * @return com.y3tu.yao.upms.model.vo.PageVo<com.y3tu.yao.upms.model.vo.UserQueryVO>
     * @author zht
     * @date 2019/11/22
     */
    PageVO<UserQueryVO> pageByCondition(UserQuery userQuery);

    /**
     * 功能描述 :重置密码
     *
     * @param id
     * @return void
     * @author zht
     * @date 2019/11/22
     */
    void resetPassWord(String id);

    /**
     * 功能描述 :添加用户
     *
     * @param query
     * @return void
     * @author zht
     * @date 2019/11/22
     */
    void addUser(UserAddQuery query);

    /**
     * 功能描述 :修改用户信息
     *
     * @param query
     * @return void
     * @author zht
     * @date 2019/11/23
     */
    void updateUser(UserUpdateQuery query);

    /**
     * 功能描述 :修改状态
     *
     * @param uid   uid
     * @param state 状态 0正常 ,1冻结
     * @return void
     * @author zht
     * @date 2019/11/23
     */
    void changeState(String uid, Integer state);


    /**
     *功能描述 :修改登录信息
     * @author zht
     * @date 2019/12/3
     * @param userID
     * @param ip
     * @return void
     */
    void updateLoginInfo(String userID, String ip);

}
