package com.y3tu.yao.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.yao.common.constants.SecurityConstants;
import com.y3tu.yao.gateway.exception.ValidateCodeException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author y3tu
 * 验证码处理
 */
@Slf4j
@Component
@AllArgsConstructor
public class ValidateCodeGatewayFilter implements GlobalFilter {

    @Autowired
    private final ObjectMapper objectMapper;
    @Autowired
    private final RedisTemplate redisTemplate;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		// 不是登录请求，直接向下执行
		if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath()
				, SecurityConstants.OAUTH_TOKEN_URL)) {
			return chain.filter(exchange);
		}

		// 刷新token，直接向下执行
		String grantType = request.getQueryParams().getFirst("grant_type");
		if (StrUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
			return chain.filter(exchange);
		}

		// 终端设置不校验， 直接向下执行
		try {
			//校验验证码
			checkCode(request);
		} catch (Exception e) {
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
			try {
				return response.writeWith(Mono.just(response.bufferFactory()
						.wrap(objectMapper.writeValueAsBytes(
								R.error(e.getMessage())))));
			} catch (JsonProcessingException e1) {
				log.error("对象输出异常", e1);
			}
		}

		return chain.filter(exchange);
	}

    /**
     * 检查code
     *
     * @param request
     */
    @SneakyThrows
    private void checkCode(ServerHttpRequest request) {
        String code = request.getQueryParams().getFirst("code");

        if (StrUtil.isBlank(code)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        String randomStr = request.getQueryParams().getFirst("randomStr");
        if (StrUtil.isBlank(randomStr)) {
            randomStr = request.getQueryParams().getFirst("mobile");
        }

        String key = SecurityConstants.DEFAULT_CODE_KEY + randomStr;
        if (!redisTemplate.hasKey(key)) {
            throw new ValidateCodeException("验证码已失效，请重新获取验证码");
        }

        Object codeObj = redisTemplate.opsForValue().get(key);

        if (codeObj == null) {
            throw new ValidateCodeException("未生成验证码");
        }

        String saveCode = codeObj.toString();
        if (StrUtil.isBlank(saveCode)) {
            redisTemplate.delete(key);
            throw new ValidateCodeException("验证码不合法");
        }

        if (!StrUtil.equals(saveCode, code)) {
            throw new ValidateCodeException("验证码不正确");
        }

        redisTemplate.delete(key);
    }
}
