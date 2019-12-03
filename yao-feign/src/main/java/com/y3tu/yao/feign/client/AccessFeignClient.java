package com.y3tu.yao.feign.client;

import com.y3tu.yao.feign.model.Access;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * ClassName: AccessFeignClient
 * Description:
 * date: 2019/10/11 14:09
 *
 * @author zht
 */
@FeignClient(name = "yao-service-provider")
public interface AccessFeignClient {

    /**
     * 功能描述 :
     *
     * @param
     * @return List<Access>
     * @author zht
     * @date 2019/10/11
     */
    @GetMapping(value = "/discovery/access")
    List<Access> listAccess();
}
