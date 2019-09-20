package com.y3tu.yao.authorization.controller;

import cn.hutool.core.util.RandomUtil;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.yao.authorization.exception.ValidateCodeException;
import com.y3tu.yao.authorization.service.UserService;
import com.y3tu.yao.authorization.service.ValidateCodeService;
import com.y3tu.yao.common.constants.AuthConstants;
import com.y3tu.yao.common.enums.SmsMessageChannnelEnum;
import com.y3tu.yao.common.enums.SmsTemplateEnum;
import com.y3tu.yao.common.template.sms.SmsMessageTemplate;
import com.y3tu.yao.common.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 授权服务Controller
 * @author y3tu
 */
@RestController
@Slf4j
public class AuthController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ConsumerTokenServices consumerTokenServices;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidateCodeService validateCodeService;

    @DeleteMapping("/token/{token}")
    public R<Boolean> removeAccessToken(@PathVariable("token") String token) {
        return new R<>(consumerTokenServices.revokeToken(token));
    }

    /**
     * 发送图形验证码
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }

    /**
     * 发送手机验证码
     * @param mobile
     * @return
     */
    @GetMapping("/mobile/{mobile}")
    public R sendMobileCode(@PathVariable("mobile") String mobile){
        Object originCode  = redisTemplate.opsForValue().get(AuthConstants.REDIS_MOBILE_CODE_PREFIX + mobile);
        if(originCode != null) {
            log.info("手机号{}验证码{}尚未失效，请失效后再申请。", mobile, originCode);
            return R.error("验证码尚未失效:"+originCode);
        }
        UserVO userVO = userService.loadUserByMobile(mobile);
        if(userVO == null) {
            log.error("手机号为{} 用户不存在", mobile);
            return R.error("手机号不存在");
        }
        String code = RandomUtil.randomNumbers(4);
        String[] params = {code};
        SmsMessageTemplate smsMessageTemplate = new SmsMessageTemplate();
        smsMessageTemplate.setParams(params);
        smsMessageTemplate.setMobile(mobile);
        smsMessageTemplate.setSignName(SmsTemplateEnum.LOGIN_CODE.getSignName());
        smsMessageTemplate.setTemplate(SmsTemplateEnum.LOGIN_CODE.getTempalte());
        smsMessageTemplate.setChannel(SmsMessageChannnelEnum.TENCENT_CLOUD.getCode());

        // 发送消息处理中心，存储在消息队列，供真正的短信程序获取队列数据并发送短信
//        rabbitTemplate.convertAndSend(MqQueueNameConstant.MOBILE_CODE_QUEUE,smsMessageTemplate);
        // 存redis
        redisTemplate.opsForValue().set(AuthConstants.REDIS_MOBILE_CODE_PREFIX+mobile, code, AuthConstants.REDIS_MOBILE_CODE_EXPIRE, TimeUnit.SECONDS);
        return R.success(code);
    }
}
