package com.y3tu.yao.feign.client;

import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.feign.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * feign 后台调用服务
 * </p>
 *
 * @author zouht
 */
@FeignClient(name = ServerNameConstants.UPMS_SERVER)
public interface UserFeignClient {

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return
     */
    @GetMapping("/user/findUserByUsername/{username}")
    UserVO loadUserByUsername(@PathVariable(value = "username") String username);

    /**
     * 通过mobile查找用户
     *
     * @param mobile
     * @return
     */
    @GetMapping("/user/loadUserByMobile/{mobile}")
    UserVO loadUserByMobile(@PathVariable(value = "mobile") String mobile);

    /**
     * 功能描述 :修改用户登录信息
     *
     * @param userID
     * @param ip
     * @return void
     * @author zht
     * @date 2019/12/3
     */
    @PutMapping("/user/update/login")
    void updateLoginInfo(@RequestParam("userID") Integer userID, @RequestParam("ip") String ip);

}
