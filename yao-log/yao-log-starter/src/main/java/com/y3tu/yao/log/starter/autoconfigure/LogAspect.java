package com.y3tu.yao.log.starter.autoconfigure;

import com.y3tu.tool.core.exception.ExceptionUtil;
import com.y3tu.tool.core.http.IpUtil;
import com.y3tu.tool.core.util.JsonUtil;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.yao.common.util.UserUtil;
import com.y3tu.yao.log.starter.constant.LogQueueNameConstant;
import com.y3tu.yao.log.starter.constant.LogStatusEnum;
import com.y3tu.yao.log.starter.model.dto.LogDTO;
import com.y3tu.yao.log.starter.constant.SaveModeEnum;
import com.y3tu.yao.log.starter.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志处理Aop
 *
 * @author y3tu
 */
@Aspect
@Slf4j
public class LogAspect {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private LogPropertiesConfig logPropertiesConfig;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.y3tu.yao.log.starter.annotation.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param pjp
     */
    @Around("logPointcut()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        Object result = null;
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Class clazz = methodSignature.getDeclaringType();

        long startTime = System.currentTimeMillis();
        LogDTO logDto = new LogDTO();
        // 需要记录日志存库
        if (targetMethod.isAnnotationPresent(Log.class)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            //获取类注解
            Log logAnnotationClass = (Log) clazz.getAnnotation(Log.class);

            // 获取方式注解
            Log logAnnotation = targetMethod.getAnnotation(Log.class);

            String serverName = logAnnotation.serverName();
            String moduleName = logAnnotation.moduleName();
            if (StrUtil.isEmpty(serverName)) {
                serverName = logAnnotationClass.serverName();
            }
            if (StrUtil.isEmpty(moduleName)) {
                moduleName = logAnnotationClass.moduleName();
            }
            logDto.setServerName(serverName)
                    .setModuleName(moduleName)
                    .setActionName(logAnnotation.actionName())
                    .setActionType(logAnnotation.actionType().getValue())
                    .setParams(JsonUtil.toJson(request.getParameterMap()))
                    .setRemoteAddr(IpUtil.getIpAddr(request))
                    .setMethod(request.getMethod())
                    .setRequestUri(request.getRequestURI())
                    .setUserAgent(request.getHeader("user-agent"));

            //通过请求解析token获取当前用户名
            try {
                String username = UserUtil.getUserName(request);
                logDto.setUsername(username);
            } catch (Exception e) {
                log.error("日志记录获取当前用户名失败", e);
            }
        }
        try {
            result = pjp.proceed();
            logDto.setStatus(LogStatusEnum.SUCCESS.getCode());
        } catch (Throwable e) {
            logDto.setException(ExceptionUtil.getStackTrace(e));
            logDto.setStatus(LogStatusEnum.FAIL.getCode());
        }
        // 本次操作用时（毫秒）
        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("[{}]use time: {}", pjp.getSignature(), elapsedTime);
        logDto.setTime(String.valueOf(elapsedTime));


        // 发送消息到 系统日志队列
        Log logAnnotation = targetMethod.getAnnotation(Log.class);
        String logQueue = "";
        if (logAnnotation.saveMode() == SaveModeEnum.DB) {
            logQueue = LogQueueNameConstant.DB_LOG_QUEUE;
        } else if (logAnnotation.saveMode() == SaveModeEnum.ES) {
            logQueue = LogQueueNameConstant.ES_LOG_QUEUE;
        } else {
            if (StrUtil.isNotEmpty(logPropertiesConfig.getSaveMode())) {
                String saveMode = logPropertiesConfig.getSaveMode();
                if ("ES".equals(saveMode)) {
                    logQueue = LogQueueNameConstant.ES_LOG_QUEUE;
                } else {
                    logQueue = LogQueueNameConstant.DB_LOG_QUEUE;
                }
            } else {
                logQueue = LogQueueNameConstant.DB_LOG_QUEUE;
            }
        }
        rabbitTemplate.convertAndSend(logQueue, logDto);
        return result;
    }
}
