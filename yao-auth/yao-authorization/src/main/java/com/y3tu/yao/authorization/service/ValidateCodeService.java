package com.y3tu.yao.authorization.service;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.yao.authorization.exception.ValidateCodeException;
import com.y3tu.yao.authorization.properties.ValidateCodeProperties;
import com.y3tu.yao.common.constants.AuthConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务
 *
 * @author y3tu
 * @date 2019-09-20
 */
@Service
public class ValidateCodeService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ValidateCodeProperties code;

    /**
     * 生成验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        setHeader(response, code.getType());
        Captcha captcha = createCaptcha(code);

        //保存验证码信息
        String randomStr = request.getParameter("randomStr");
        //验证码保存到redis并设置失效时间30秒
        redisTemplate.opsForValue().set(AuthConstants.VALIDATE_CODE_KEY + randomStr, captcha.text(), 30, TimeUnit.SECONDS);
        captcha.out(response.getOutputStream());
    }

    /**
     * 校验验证码
     *
     * @param key   前端上送 key
     * @param value 前端上送待校验值
     */
    public void check(String key, String value) throws ValidateCodeException {
        Object codeInRedis = redisTemplate.opsForValue().get(AuthConstants.VALIDATE_CODE_KEY + key);
        if (StrUtil.isBlank(value)) {
            throw new ValidateCodeException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StrUtil.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new ValidateCodeException("验证码不正确");
        }
    }

    private Captcha createCaptcha(ValidateCodeProperties code) {
        Captcha captcha = null;
        if (StrUtil.equalsIgnoreCase(code.getType(), AuthConstants.VALIDATE_CODE_TYPE_GIF)) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

    private void setHeader(HttpServletResponse response, String type) {
        if (StrUtil.equalsIgnoreCase(type, AuthConstants.VALIDATE_CODE_TYPE_GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }
}
