package com.y3tu.yao.feign.client;

import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.feign.vo.RoleVO;
import feign.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName: UserRoleService
 * Description:
 * date: 2019/11/22 9:29
 *
 * @author zht
 */
@FeignClient(name = ServerNameConstants.BACK_SERVER, configuration = UserRoleFeignClient.UserRoleFeignConfig.class)
public interface UserRoleFeignClient {

    /**
     * 根据用户Id获取用户所属角色
     *
     * @param userId 用户id
     * @return RoleVO
     */
    @GetMapping("/role/{userId}")
    RoleVO findByUserId(@PathVariable(value = "userId") String userId);

    class UserRoleFeignConfig {
        @Bean
        public Logger.Level logger() {
            return Logger.Level.FULL;
        }
    }
}
