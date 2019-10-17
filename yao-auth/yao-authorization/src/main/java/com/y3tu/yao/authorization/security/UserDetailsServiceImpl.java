package com.y3tu.yao.authorization.security;

import com.y3tu.yao.authorization.service.UserService;
import com.y3tu.yao.common.constants.ServerNameConstants;
import com.y3tu.yao.common.vo.UserVO;
import com.y3tu.tool.core.exception.ServerCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author y3tu
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Feign Interface ,调用具体的服务
     */
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVO = null;
        try {
            userVO = userService.loadUserByUsername(username);
        } catch (Exception e) {
            throw new ServerCallException("服务[" + ServerNameConstants.BACK_SERVER + "]调用异常！", e);
        }
        if (userVO == null) {
            throw new UsernameNotFoundException("未查询到此用户");
        }
        UserDetailsImpl userDetails = new UserDetailsImpl(userVO);
        log.info("UserDetails {}", userDetails);
        return userDetails;
    }
}
