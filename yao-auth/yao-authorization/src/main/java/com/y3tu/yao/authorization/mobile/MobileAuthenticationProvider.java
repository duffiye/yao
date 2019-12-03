package com.y3tu.yao.authorization.mobile;

import com.y3tu.yao.common.constants.AuthConstants;
import com.y3tu.yao.common.security.UserDetailsImpl;
import com.y3tu.yao.feign.client.UserFeignClient;
import com.y3tu.yao.feign.vo.UserVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * 手机验证码登录逻辑实现
 *
 * @author y3tu
 */
@Slf4j
@Data
public class MobileAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken = (MobileAuthenticationToken) authentication;
        String mobile = mobileAuthenticationToken.getPrincipal().toString();
        Object realCode = redisTemplate.opsForValue().get(AuthConstants.REDIS_MOBILE_CODE_PREFIX + mobile);
        String inputCode = authentication.getCredentials().toString();
        // 判断手机的验证码是否存在
        if (realCode == null) {
            log.error("登录失败，当前手机号验证码不存在或者已经过期");
            throw new BadCredentialsException("登录失败，验证码不存在");
        }
        // 判断是否验证码跟redis中存的验证码是否正确
        if (!inputCode.equalsIgnoreCase(realCode.toString())) {
            log.debug("登录失败，您输入的验证码不正确");
            throw new BadCredentialsException("登录失败，验证码不正确");
        }
        UserVO userVO = userFeignClient.loadUserByMobile(mobile);
        if (userVO == null) {
            log.error("登录失败，用户不存在");
            throw new UsernameNotFoundException("登录失败, 手机号码不存在");
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(userVO);
        // 重新构造token  登录成功
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, inputCode, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
