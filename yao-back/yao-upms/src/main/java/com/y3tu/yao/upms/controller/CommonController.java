package com.y3tu.yao.upms.controller;

import com.y3tu.tool.core.http.IpUtil;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.yao.common.constants.ServerNameConstants;
import com.y3tu.yao.log.starter.annotation.Log;
import com.y3tu.yao.log.starter.constant.ActionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用Controller
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/common")
@Log(serverName = ServerNameConstants.UPMS_SERVER, moduleName = "通用查询")
public class CommonController {

    @Autowired
    HttpServletRequest request;

    /**
     * 获取请求的ip地址
     *
     * @param request
     * @return
     */
    @GetMapping("/getIp")
    public R getIp(HttpServletRequest request) {
        return R.success(IpUtil.getIpAddr(request));
    }

    /**
     * 获取请求所在城市的天气情况
     *
     * @param request
     * @return
     */
    @GetMapping("/getWeather")
    @Log(actionName = "获取天气信息", actionType = ActionTypeEnum.VIEW)
    public R getWeather(HttpServletRequest request) {
        return R.success(IpUtil.getIpWeatherInfo(IpUtil.getIpAddr(request)));
    }

    /**
     * 获取请求的所在城市
     *
     * @param request
     * @return
     */
    @GetMapping("/getCity")
    public R getCity(HttpServletRequest request) {
        return R.success(IpUtil.getCityInfo(IpUtil.getIpAddr(request)));
    }
}
