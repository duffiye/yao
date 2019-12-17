package com.y3tu.yao.gateway.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.y3tu.yao.common.constants.CacheKeyConstants.GatewayCacheKey.NONCE_PREFIX;

/**
 * ClassName: NpceUtil
 * Description:
 * date: 2019/11/20 15:07
 *
 * @author zht
 */
@Component
public class NonceUtil {

    private static RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        NonceUtil.redisTemplate = redisTemplate;
    }

    public static boolean exist(String nonce) {
        String oldNonce = redisTemplate.opsForValue().get(NONCE_PREFIX + nonce);
        return StringUtils.isNotEmpty(oldNonce);
    }

    public static void addNonce(String key, long timeLimit, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, "1", timeLimit, timeUnit);
    }
}
