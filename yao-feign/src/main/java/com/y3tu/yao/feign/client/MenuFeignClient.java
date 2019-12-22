package com.y3tu.yao.feign.client;

import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.feign.vo.MenuVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * @author
 */
@FeignClient(name = ServerNameConstants.UPMS_SERVER)
public interface MenuFeignClient {
    /**
     * 通过角色名查询资源信息
     *
     * @param roleCode 角色编码
     * @return 菜单列表
     */
    @GetMapping("/resource/role/{roleCode}")
    Set<MenuVO> listMenuByRole(@PathVariable("roleCode") String roleCode);

    /**
     * 获取全部资源信息
     *
     * @return
     */
    @GetMapping("/resource/list")
    Set<MenuVO> listAllMenu();
}
