package com.y3tu.yao.gateway.filter;

import com.y3tu.tool.core.exception.ServerCallException;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.yao.common.config.FilterIgnorePropertiesConfig;
import com.y3tu.yao.common.util.TimeUtils;
import com.y3tu.yao.feign.client.AuthenticationFeignClient;
import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.gateway.exception.*;
import com.y3tu.yao.gateway.utils.NonceUtil;
import com.y3tu.yao.gateway.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.y3tu.yao.common.constants.CacheKeyConstants.GatewayCacheKey.NONCE_PREFIX;
import static com.y3tu.yao.common.constants.CharsetConstant.UTF_8;
import static com.y3tu.yao.common.constants.DateConstant.PURE_DATETIME_MS_PATTERN;

/**
 * 网关权限过滤器
 *
 * @author y3tu
 */
@Component
@Slf4j
@Order(-5)
public class AccessGatewayFilter implements GlobalFilter {

    @Autowired
    AuthenticationFeignClient authenticationFeignClient;

    @Autowired
    FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    private final SimpleDateFormat sdf = new SimpleDateFormat(PURE_DATETIME_MS_PATTERN);

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
        HttpMethod method = request.getMethod();

        if (Objects.isNull(method)) {
            throw new ErrorHTTPMethodException(SystemError.HTTP_METHOD_ERROR);
        }

        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        String url = request.getPath().value();
        log.debug("url:{},method:{},headers:{}", url, method, request.getHeaders());
        //不需要网关签权的url
        if (this.ignoreAuthentication(url)) {
            return chain.filter(exchange);
        }
        boolean hasPermission;
        try {
            //调用签权服务看用户是否有权限，若有权限进入下一个filter
            hasPermission = authenticationFeignClient.hasPermission(authentication, url, method.name());
            //验证参数和防止重复提交

        } catch (Exception e) {
            String msg = e.getMessage();
            //判断是不是token过期失效
            if (msg.contains("401")) {
                throw new UnAuthorizedException(SystemError.UNAUTHORIZED);
            }
            String serverName = ServerNameConstants.AUTHENTICATION_SERVER;
            String str = StrUtil.format("{}服务调用{}异常,参数：authentication:{},url{},method{}", serverName, "hasPermission", authentication, url, method);
            throw new ServerCallException(str, e);
        }

        if (hasPermission) {
            // 验证签名
            ServerRequest serverRequest = new DefaultServerRequest(exchange);
            String timestamp = request.getHeaders().getFirst("timestamp");
            String nonce = request.getHeaders().getFirst("nonce");
            String sign = request.getHeaders().getFirst("sign");
            // read body and verify sign
            //POST & PUT & PATCH 请求且为表单或者JSON传参取body验证签名.即排除文件上传的接口.
            if (HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method) || HttpMethod.PATCH.equals(method)
                    && (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType)
                    || MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType))) {

                Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                        .flatMap(body -> {
                            // 签名验证
                            apiAuth(timestamp, nonce, body, sign);
                            return Mono.just(body);
                        });
                // 由于上面请求已经被消费,将请求信息重新塞回请求中
                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());
                headers.remove(HttpHeaders.CONTENT_LENGTH);

                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        .then(Mono.defer(() -> {
                            ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                                    exchange.getRequest()) {

                                public HttpHeaders getHeaders() {
                                    long contentLength = headers.getContentLength();
                                    HttpHeaders httpHeaders = new HttpHeaders();
                                    httpHeaders.putAll(super.getHeaders());
                                    if (contentLength > 0) {
                                        httpHeaders.setContentLength(contentLength);
                                    } else {
                                        httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                                    }
                                    return httpHeaders;
                                }

                                public Flux<DataBuffer> getBody() {
                                    return outputMessage.getBody();
                                }
                            };
                            return chain.filter(exchange.mutate().request(decorator).build());
                        }));
            } else if (HttpMethod.GET.equals(method) || HttpMethod.DELETE.equals(method)) {
                // 验证如GET ,DELETE 在链接中传参的请求
                String path = request.getURI().getPath();
                String query = request.getURI().getQuery();
                apiAuth(timestamp, nonce, path + "?" + query, sign);
                return chain.filter(exchange);
            }
            return chain.filter(exchange);
            //如果每个微服务需要做验证，则转发的请求都加上服务间认证token
            //如果只在网关做验证则不需要
        } else {
            throw new NoPermissionException(SystemError.NO_PERMISSION);
        }
    }


    private boolean ignoreAuthentication(String url) {
        return filterIgnorePropertiesConfig.getUrls().stream().anyMatch(ignoreUrl -> url.startsWith(StrUtil.trim(ignoreUrl)));
    }


    private void apiAuth(String timestamp, String nonce, String bodyString, String sign) {

        //时间限制配置
        long timeLimit = 3 * 60 * 1000L;
        long cacheTime = 1000;

        //请求头参数非空验证
        if (StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(sign)) {
            throw new HeadersErrorException(SystemError.HEADERS_ERROR);
        }

        //请求时间和现在时间对比验证，发起请求时间和服务器时间不能超过timeLimit秒(防止前端挂起请求篡改报文)
        long betweenMinutes = TimeUtils.getBetweenMilliSeconds(sdf.format(new Date()), timestamp);
        System.out.println(betweenMinutes);
        try {
            if (TimeUtils.getBetweenMilliSeconds(new Date(), sdf.parse(timestamp)) > timeLimit) {
                throw new TimeLimitException(SystemError.TIME_ERROR);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //验证相同nonce的请求是否已经存在，存在表示为重复请求
        if (NonceUtil.exist(nonce)) {
            throw new RepeatRequestException(SystemError.REPEAT_REQUEST);
        } else {
            //如果nonce没有在缓存中，则需要加入，并设置过期时间为cacheTime秒,防止连续的重复请求
            NonceUtil.addNonce(NONCE_PREFIX + nonce, cacheTime, TimeUnit.MILLISECONDS);
        }

        //服务器生成签名与header中签名对比
        String serverSign = SignUtil.createSign(UTF_8, timestamp, nonce, bodyString);
        System.out.println(serverSign);
        if (!serverSign.equals(sign)) {
            throw new ErrorSignException(SystemError.SIGN_ERROR);
        }
    }

}
