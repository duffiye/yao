package com.y3tu.yao.authentication.service;

import com.y3tu.yao.feign.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author y3tu
 * @date 2019-05-09
 */
public interface AuthenticationService {
    /**
     * 校验权限
     *
     * @param authRequest
     * @return 是否有权限
     */
    boolean decide(HttpServletRequest authRequest);

    /**
     * 功能描述 :获取登录用户信息
     *
     * @param
     * @return com.y3tu.tool.core.pojo.R
     * @author zht
     * @date 2019/11/30
     */
    UserVO getUser();

}
