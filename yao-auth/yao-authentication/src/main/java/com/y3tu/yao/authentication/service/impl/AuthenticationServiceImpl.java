package com.y3tu.yao.authentication.service.impl;

import com.y3tu.yao.authentication.feign.ResourceService;
import com.y3tu.yao.authentication.service.AuthenticationService;
import com.y3tu.yao.common.vo.ResourceVO;
import com.y3tu.tool.core.collection.CollectionUtil;
import com.y3tu.tool.core.collection.IterUtil;
import com.y3tu.tool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author y3tu
 * @date 2019-05-09
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {


    @Autowired
    ResourceService resourceService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * Authorization认证开头是"bearer "
     */
    private static final int BEARER_BEGIN_INDEX = 7;

    /**
     * jwt token 密钥，主要用于token解析，签名验证
     */
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    /**
     * jwt验签
     */
    private MacSigner verifier;

    /**
     * @param authRequest
     * @return true：有权限 false：无权限
     */
    @Override
    public boolean decide(HttpServletRequest authRequest) {
        log.debug("正在访问的url是:{}，method:{}", authRequest.getServletPath(), authRequest.getMethod());

        // 前端跨域OPTIONS请求预检放行 也可通过前端配置代理实现
        // 在这里放行具有一定风险,也可通过前端配置代理实现
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(authRequest.getMethod())) {
            return true;
        }
        //获取用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if (principal != null) {
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return false;
            }

            Set<ResourceVO> paths = new HashSet<>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                //如果是管理员角色ROLE_ADMIN 则具有此系统的所有权限 直接放行
                if (StrUtil.equals(authority.getAuthority(), "ROLE_ADMIN")) {
                    hasPermission = true;
                    break;
                }

                // 角色与菜单权限的关联关系需要缓存提高访问效率,可以考虑把资源数据放在redis中
                Set<ResourceVO> resourceVOS = resourceService.listResourceByRole(authority.getAuthority());
                if (IterUtil.isNotEmpty((resourceVOS))) {
                    CollectionUtil.addAll(paths, resourceVOS, null);
                }
            }

            //如果请求的url不在资源列表之中，那么直接放行
            Set<ResourceVO> allResourceVOS = resourceService.listAllResource();
            boolean isNeedPermission = false;
            for (ResourceVO resourceVO : allResourceVOS) {
                if (StrUtil.isNotEmpty(resourceVO.getPath()) && antPathMatcher.match(resourceVO.getPath(), authRequest.getServletPath())) {
                    isNeedPermission = true;
                }
            }
            if(!isNeedPermission){
                return true;
            }

            ///如果请求的url在资源列表之中,那么判断用户所属角色是否拥有访问此资源的权限
            for (ResourceVO resourceVO : paths) {
                if (StrUtil.isNotEmpty(resourceVO.getPath()) && antPathMatcher.match(resourceVO.getPath(), authRequest.getServletPath())
                        && authRequest.getMethod().equalsIgnoreCase(resourceVO.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
