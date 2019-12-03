package com.y3tu.yao.upms.controller;

import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.log.starter.annotation.Log;
import com.y3tu.yao.upms.model.entity.Resource;
import com.y3tu.yao.upms.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@RestController
@RequestMapping("/resource")
@Log(serverName = ServerNameConstants.UPMS_SERVER, moduleName = "资源管理")
public class ResourceController extends BaseController<ResourceService, Resource> {

    @Autowired
    ResourceService resourceService;

    /**
     * 当前登录用户的菜单权限资源
     *
     * @return
     */
    @RequestMapping(path = "/current", method = {RequestMethod.GET, RequestMethod.POST})
    public R currentResource() {

        return R.success(resourceService.currentResource());
    }

}
