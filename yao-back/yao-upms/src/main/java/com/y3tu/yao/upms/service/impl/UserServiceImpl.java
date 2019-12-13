package com.y3tu.yao.upms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.y3tu.tool.core.collection.CollectionUtil;
import com.y3tu.tool.core.exception.BusinessException;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import com.y3tu.yao.common.enums.UserStatusEnum;
import com.y3tu.yao.feign.vo.UserVO;
import com.y3tu.yao.upms.mapper.*;
import com.y3tu.yao.upms.model.dto.UserDTO;
import com.y3tu.yao.upms.model.entity.*;
import com.y3tu.yao.upms.model.query.user.UserAddQuery;
import com.y3tu.yao.upms.model.query.user.UserQuery;
import com.y3tu.yao.upms.model.query.user.UserUpdateQuery;
import com.y3tu.yao.upms.model.vo.PageVO;
import com.y3tu.yao.upms.model.vo.UserQueryVO;
import com.y3tu.yao.upms.service.ResourceService;
import com.y3tu.yao.upms.service.RoleService;
import com.y3tu.yao.upms.service.UserRoleService;
import com.y3tu.yao.upms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.y3tu.yao.common.constants.DateConstant.NORM_DATETIME_PATTERN;
import static com.y3tu.yao.upms.constant.UpmsConstant.RoleCode.SUPER_ADMIN;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    BaseUserMapper baseUserMapper;

    @Autowired
    STFStaffMapper stfStaffMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public PageVO<UserQueryVO> pageByCondition(UserQuery userQuery) {
        Integer userId = userQuery.getUserId();

        User user = userMapper.selectById(userId);
        if (user.getRoleCode().equals(SUPER_ADMIN)){
            PageHelper.startPage(userQuery.getPageNum(), userQuery.getPageSize(), userQuery.getPageNum() != 0);
            return new PageVO<>(userMapper.selectByCondition(userQuery));
        }else{
            PageHelper.startPage(userQuery.getPageNum(), userQuery.getPageSize(), userQuery.getPageNum() != 0);
            return new PageVO<>(userMapper.selectSelf(userId));
        }
    }

    @Override
    public void resetPassWord(String id) {
        // 查询出该用户
        User user =userMapper.selectById(id);
        // 截取手机后六位做加密
        String phone = user.getPhone();
        String passStr = phone.substring(phone.length() - 6, phone.length());
        String newPassWord = encoder.encode(passStr);
        user.setPassword(newPassWord);
        // 保存
        userMapper.updateById(user);
    }

    @Override
    public void addUser(UserAddQuery query) {

        String username = query.getUsername();

        if (Objects.nonNull(userMapper.selectUserVoByUsername(username))) {
            throw new BusinessException("用户名已经存在,请勿重复添加");
        }

        User user = new User();

        // 插入base_user表
        BaseUser baseUser = new BaseUser();
        String openID = UUID.randomUUID().toString().replaceAll("-", "");
        baseUser.setOpenID(openID);
        baseUser.setPhone(query.getPhone());
        baseUserMapper.insert(baseUser);

        // 插入stfStaff 会员表
        STFStaff stfStaff = new STFStaff();
        stfStaff.setOpenID(openID);
        stfStaffMapper.insert(stfStaff);

        //插入sys_user表
        User sysUser = new User();
        sysUser.setUid(baseUser.getId());
        // 复制属性
        BeanUtil.copyProperties(query, user);
        // 加密密码
        user.setPassword( encoder.encode(user.getPassword()));
        // 入库
        userMapper.insert(user);
    }

    @Override
    public void updateUser(UserUpdateQuery query) {
        String uid = query.getUid();
        User user = userMapper.selectUserByUid(uid);
        BeanUtil.copyProperties(query, user);
        userMapper.updateById(user);
    }

    @Override
    public void changeState(String uid, Integer state) {
        User user = userMapper.selectUserByUid(uid);
        user.setState(state);
        userMapper.updateById(user);
    }


    @Override
    public List<UserDTO> selectAll() {
        List<User> list = this.baseMapper.selectAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        list.forEach(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtil.copyProperties(user, userDTO);
            userDTOList.add(userDTO);
        });
        return userDTOList;
    }

    @Override
    public UserVO findUserByUsername(String username) {
        return userMapper.selectUserVoByUsername(username);
    }

    @Override
    public UserVO findUserById(String userId) {
        return userMapper.selectUserVoById(userId);
    }

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @Override
    public UserVO findUserByMobile(String mobile) {
        return userMapper.selectUserVoByMobile(mobile);
    }

    /**
     * 通过openId查询用户
     *
     * @param openId openId
     * @return 用户信息
     */
    @Override
    public UserVO findUserByOpenId(String openId) {
        return userMapper.selectUserVoByOpenId(openId);
    }

    /**
     *功能描述 :修改用户登录信息
     * @author zht
     * @date 2019/12/3
     * @param userId
     * @param ip
     * @return void
     */
    @Override
    public void updateLoginInfo(String userId,String ip) {
        User user = new User();
        user.setId(userId);
        user.setLastLoginIpAt(ip);
        user.setLastLoginIpAt(new SimpleDateFormat(NORM_DATETIME_PATTERN).format(new Date()));
        userMapper.updateById(user);
    }


}
