package com.y3tu.yao.log.server.controller;

import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.log.server.model.entity.Log;
import com.y3tu.yao.log.server.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志Controller
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/log")
@Api(value = "日志controller", tags = {"系统日志操作接口"})
public class LogController extends BaseController<LogService, Log> {

    private static final String MODULE_NAME = "系统日志模块";

    @com.y3tu.yao.log.starter.annotation.Log(actionName = "查看用户操作日志信息", serverName = ServerNameConstants.LOG_SERVER, moduleName = MODULE_NAME)
    @ApiOperation(value = "日志信息分页查询", notes = "日志信息分页查询", httpMethod = "POST")
    @ApiImplicitParam(name = "pageInfo", value = "日志信息查询类", dataType = "PageInfo")
    @PostMapping("/page")
    @Override
    public R page(@RequestBody PageInfo pageInfo) {
        return R.success(service.page(pageInfo));
    }

}