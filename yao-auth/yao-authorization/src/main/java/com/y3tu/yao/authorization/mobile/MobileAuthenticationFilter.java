package com.y3tu.yao.authorization.mobile;

import com.y3tu.yao.common.constants.AuthConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.alibaba.nacos.common.util.HttpMethod.POST;


/**
 * 手机验证码登录filter
 * @author y3tu
 */
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    public MobileAuthenticationFilter() {
        super(AuthConstants.MOBILE_TOKEN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String mobile = request.getParameter("mobile");
        String code = request.getParameter("code");

        if (mobile == null) {
            mobile = "";
        }

        if (code == null) {
            code = "";
        }

        mobile = mobile.trim();
        code = code.trim();

        MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile, code);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public boolean isPostOnly() {
        return postOnly;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
