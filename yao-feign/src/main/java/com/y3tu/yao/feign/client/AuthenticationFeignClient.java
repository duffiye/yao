package com.y3tu.yao.feign.client;

import com.y3tu.yao.feign.constant.ServerNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zouht
 * @date 2019-05-09
 */
@FeignClient(name = ServerNameConstants.UPMS_SERVER)
public interface AuthenticationFeignClient {

    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param authentication
     * @param url
     * @param method
     * @return <pre>
     * </pre>
     */
    @PostMapping(value = "/hasPermission")
    boolean hasPermission(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);

}
