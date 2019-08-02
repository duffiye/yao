package com.y3tu.yao.log.starter.annotation;

import com.y3tu.yao.log.starter.constant.ActionTypeEnum;
import com.y3tu.yao.log.starter.constant.SaveModeEnum;

import java.lang.annotation.*;

/**
 * @author y3tu
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 服务名
     *
     * @return
     */
    String serverName() default "";

    /**
     * 模块名
     *
     * @return
     */
    String moduleName() default "";

    /**
     * 操作名
     *
     * @return
     */
    String actionName() default "";

    /**
     * 操作类型
     *
     * @return
     */
    ActionTypeEnum actionType() default ActionTypeEnum.VIEW;

    /**
     * 保存日志方式 默认保存到数据库
     */
    SaveModeEnum saveMode() default SaveModeEnum.NONE;

}
