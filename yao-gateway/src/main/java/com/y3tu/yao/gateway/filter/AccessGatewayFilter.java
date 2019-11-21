package com.y3tu.yao.gateway.filter;

import com.y3tu.tool.core.exception.ServerCallException;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.yao.common.config.FilterIgnorePropertiesConfig;
import com.y3tu.yao.common.constants.ServerNameConstants;
import com.y3tu.yao.gateway.exception.*;
import com.y3tu.yao.gateway.feign.AuthenticationService;
import com.y3tu.yao.gateway.security.MyUser;
import com.y3tu.yao.gateway.utils.NonceUtil;
import com.y3tu.yao.gateway.utils.SignUtil;
import com.y3tu.yao.gateway.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 网关权限过滤器
 *
 * @author y3tu
 */
@Component
@Slf4j
public class AccessGatewayFilter implements GlobalFilter {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;


    private static final String NONCE_PREFIX = "NOCE:";
    private static final String CHARACTER_ENCODING = "UTF-8";

    /**
     * 1.首先网关检查token是否有效，无效直接返回401，不调用签权服务
     * 2.调用签权服务器看是否对该请求有权限，有权限进入下一个filter，没有权限返回401
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String method = request.getMethodValue();
        String url = request.getPath().value();
        log.debug("url:{},method:{},headers:{}", url, method, request.getHeaders());
        //不需要网关签权的url
        if (this.ignoreAuthentication(url)) {
            return chain.filter(exchange);
        }
        boolean hasPermission;
        boolean isPassing;
        try {
            //调用签权服务看用户是否有权限，若有权限进入下一个filter
            hasPermission = authenticationService.hasPermission(authentication, url, method);
            //验证参数和防止重复提交
            isPassing = apiAuth(exchange);

        } catch (Exception e) {
            String msg = e.getMessage();
            //判断是不是token过期失效
            if (msg.contains("401")) {
                throw new UnAuthorizedException("未授权或token过期，请重新登录！", e);
            }
            String serverName = ServerNameConstants.AUTHENTICATION_SERVER;
            String str = StrUtil.format("{}服务调用{}异常,参数：authentication:{},url{},method{}", serverName, "hasPermission", authentication, url, method);
            throw new ServerCallException(str, e);
        }

        if (hasPermission && isPassing) {
            ServerHttpRequest.Builder builder = request.mutate();
            //如果每个微服务需要做验证，则转发的请求都加上服务间认证token
            //如果只在网关做验证则不需要
            return chain.filter(exchange.mutate().request(builder.build()).build());
        } else {
            throw new NoPermissionException("当前操作没有权限");
        }
    }


    private boolean ignoreAuthentication(String url) {
        return filterIgnorePropertiesConfig.getUrls().stream().anyMatch(ignoreUrl -> url.startsWith(StrUtil.trim(ignoreUrl)));
    }

    private boolean apiAuth(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String timestamp = request.getHeaders().getFirst("timestamp");
        String nonce = request.getHeaders().getFirst("nonce");
        String sign = request.getHeaders().getFirst("sign");

        StringBuilder sb = new StringBuilder();
        if (!HttpMethod.GET.equals(request.getMethod())) {
            Flux<DataBuffer> body = request.getBody();
            body.subscribe(dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                DataBufferUtils.release(dataBuffer);
                String bodyString = new String(bytes, StandardCharsets.UTF_8);
                sb.append(bodyString);
            });
        }

        //时间限制配置
        long timeLimit = 60;

        //请求头参数非空验证
        if (StringUtils.isEmpty(authentication) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(sign)) {
            throw new HeadersErrorException("请求头部信息错误");
        }

        //请求时间和现在时间对比验证，发起请求时间和服务器时间不能超过timeLimit秒
        if (TimeUtils.timeDiffSeconds(new Date(), timestamp) > timeLimit) {
            throw new TimeLimitException("请求发起时间超过服务器限制");
        }

        //验证用户信息
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new UnAuthorizedException("未授权或token过期，请重新登录！");
        }

        //验证相同nonce的请求是否已经存在，存在表示为重复请求
        if (NonceUtil.exist(user.getUserId(), nonce)) {
            throw new RepeatRequestException("重复的请求");
        } else {
            //如果nonce没有在缓存中，则需要加入，并设置过期时间为timeLimit秒
            NonceUtil.addNonce(NONCE_PREFIX + user.getUserId(), nonce, timeLimit);
        }

        //服务器生成签名与header中签名对比
        SortedMap<Object, Object> parameters = new TreeMap<>();
        parameters.put("userId", user.getUserId());
        parameters.put("timestamp", timestamp);
        parameters.put("nonce", nonce);
        parameters.put("authentication", authentication);
        parameters.put("bodyString", sb.toString());

        String serverSign = SignUtil.createSign(CHARACTER_ENCODING, parameters);
        if (!serverSign.equals(sign)) {
            throw new ErrorSignException("错误的签名信息");
        }

        return true;
    }
}
