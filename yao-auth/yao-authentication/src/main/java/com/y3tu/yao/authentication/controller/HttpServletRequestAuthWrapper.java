package com.y3tu.yao.authentication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HttpServlet 自定义封装
 *
 * @author y3tu
 */
public class HttpServletRequestAuthWrapper extends HttpServletRequestWrapper {

    private String url;
    private String method;

    /**
     * @param url
     * @param method
     */
    public HttpServletRequestAuthWrapper(HttpServletRequest request, String url, String method) {
        super(request);
        this.url = url;
        this.method = method;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getServletPath() {
        return this.url;
    }
}
