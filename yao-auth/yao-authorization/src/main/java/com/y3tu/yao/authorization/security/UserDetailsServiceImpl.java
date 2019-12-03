package com.y3tu.yao.authorization.security;

import com.y3tu.tool.core.exception.ServerCallException;
import com.y3tu.yao.common.security.UserDetailsImpl;
import com.y3tu.yao.feign.client.UserFeignClient;
import com.y3tu.yao.feign.client.UserRoleFeignClient;
import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.feign.vo.RoleVO;
import com.y3tu.yao.feign.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author y3tu
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private UserRoleFeignClient userRoleFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVO;
        RoleVO roleVo;
        try {
            userVO = userFeignClient.loadUserByUsername(username);
            roleVo = userRoleFeignClient.findByUserId(userVO.getId().toString());
        } catch (Exception e) {
            throw new ServerCallException("服务[" + ServerNameConstants.BACK_SERVER + "]调用异常！", e);
        }
        return new UserDetailsImpl(userVO, roleVo);
    }
}
