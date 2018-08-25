package com.y3tu.cloud.upms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.MenusService;
import com.y3tu.cloud.upms.entity.Menus;




/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date  2018-08-24
 */
@RestController
@RequestMapping("/upms/menus")
public class MenusController extends BaseController<MenusService,Menus> {

}