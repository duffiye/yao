package com.y3tu.yao.authorization.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.y3tu.tool.core.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 手机登录失败处理
 * @author y3tu
 */
@Slf4j
@Component
public class MobileAuthenticationFailHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败 原因: {}", exception.getMessage());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(R.error(exception.getMessage())));
    }
}
