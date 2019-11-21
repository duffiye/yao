package com.y3tu.yao.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sys-service")
public interface SysAuthenticationService {


    @PostMapping(value = "/hasPermission")
    boolean hasPermission(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);
}
