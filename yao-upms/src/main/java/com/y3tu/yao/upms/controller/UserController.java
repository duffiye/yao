package com.y3tu.yao.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.core.util.ObjectUtil;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.yao.common.util.UserUtil;
import com.y3tu.yao.feign.vo.UserVO;
import com.y3tu.yao.upms.model.dto.UserDTO;
import com.y3tu.yao.upms.model.entity.Role;
import com.y3tu.yao.upms.model.entity.User;
import com.y3tu.yao.upms.model.entity.UserRole;
import com.y3tu.yao.upms.model.query.user.UserAddQuery;
import com.y3tu.yao.upms.model.query.user.UserQuery;
import com.y3tu.yao.upms.model.query.user.UserUpdateQuery;
import com.y3tu.yao.upms.service.RoleService;
import com.y3tu.yao.upms.service.UserRoleService;
import com.y3tu.yao.upms.service.UserService;
import com.y3tu.yao.upms.utils.BeforeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController<UserService, User> {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HttpServletRequest request;

    /**
     * 根据传入的token解析获取用户
     *
     * @return
     */
    @GetMapping(value = "/info")
    public R getUserInfo() {
        String userId = UserUtil.getUserId(request);
        return R.success(userService.findUserById(userId));
    }

    @PostMapping(value = "/register")
    public R register(@ModelAttribute User u,
                      @RequestParam String verify,
                      @RequestParam String captchaId) {

        if (StrUtil.isBlank(verify) || StrUtil.isBlank(u.getUsername())
                || StrUtil.isBlank(u.getPassword())) {
            return R.warn("缺少必需表单字段");
        }
        //从redis中获取验证码
        String code = redisTemplate.opsForValue().get(captchaId).toString();
        if (StrUtil.isBlank(code)) {
            return R.warn("验证码已过期，请重新获取");
        }
        if (!verify.toLowerCase().equals(code.toLowerCase())) {
            log.error("注册失败，验证码错误：code:" + verify + ",redisCode:" + code.toLowerCase());
            return R.warn("验证码输入错误");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("username", u.getUsername()));
        if (ObjectUtil.isNotNull(user)) {
            return R.warn("该用户名已被注册");
        }

        //密码加密
        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        userService.save(u);

        // 默认角色
        List<Role> roleList = roleService.list(new QueryWrapper<Role>().eq("default_role", true));
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                UserRole ur = new UserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(role.getId());
                userRoleService.save(ur);
            }
        }
        return R.success();
    }

    /**
     * 获取用户分页数据
     *
     * @param userQuery
     * @return
     */
    @PostMapping("/page")
    public R page(@RequestBody UserQuery userQuery) {
        String userId = UserUtil.getUserId(request);
        userQuery.setUserId(Integer.valueOf(userId));
        return R.success(userService.pageByCondition(userQuery));
    }

    @PatchMapping("/{id}")
    public R resetPassWord(@PathVariable("id") String id) {
        userService.resetPassWord(id);
        return R.success();
    }

    @PostMapping
    public R addUser(@Valid @RequestBody UserAddQuery userAddQuery, BindingResult bindingResult) {
        new BeforeValidator(bindingResult).validate();
        userService.addUser(userAddQuery);
        return R.success();
    }


    @PutMapping
    public R updateUser(@RequestBody UserUpdateQuery query) {
        userService.updateUser(query);
        return R.success();
    }

    @PatchMapping("/{uid}/{state}")
    public R changeState(@PathVariable(value = "uid") String uid,
                         @PathVariable(value = "state") Integer state) {
        userService.changeState(uid, state);
        return R.success();
    }


    @PostMapping(value = "/unlock")
    public R unLock(@RequestBody UserDTO userDTO) {

        User user = userService.getById(userDTO.getId());
        if (!new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
            return R.warn("密码不正确");
        }
        return R.success();
    }


    @DeleteMapping(value = "/delByIds/{ids}")
    @Override
    public R delByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            userService.removeById(id);
            //删除关联角色
            userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
        }
        return R.success();
    }


    /**
     * 通过用户名查询用户及其角色信息和权限(服务间调用)
     *
     * @param username 用户名
     * @return UseVo 对象
     */
    @GetMapping("/findUserByUsername/{username}")
    public UserVO findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    /**
     * 通过手机号查询用户及其角色信息(服务间调用)
     *
     * @param mobile 手机号
     * @return UseVo 对象
     */
    @GetMapping("/findUserByMobile/{mobile}")
    public UserVO findUserByMobile(@PathVariable String mobile) {
        return userService.findUserByMobile(mobile);
    }

    /**
     * 通过OpenId查询(服务间调用)
     *
     * @param openId openid
     * @return 对象
     */
    @GetMapping("/findUserByOpenId/{openId}")
    public UserVO findUserByOpenId(@PathVariable String openId) {
        return userService.findUserByOpenId(openId);
    }
}
