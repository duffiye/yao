package com.y3tu.yao.gateway.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate){
        NonceUtil.redisTemplate=redisTemplate;
    }

    public static boolean exist(String userId, String nonce){
        String oldNonce = redisTemplate.opsForValue().get(userId);
        return nonce.equals(oldNonce);
    }

    public static void addNonce(String userId, String nonce, long timeLimit){
        redisTemplate.opsForValue().set(userId,nonce,timeLimit);
    }
}
