package com.y3tu.yao.feign.client;

import com.y3tu.yao.feign.constant.ServerNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.crypto.SecretKey;

/**
 * ClassName: SecretKeyFeignClient
 * Description:
 * date: 2019/12/23 17:23
 *
 * @author zht
 */
@FeignClient(name = ServerNameConstants.COMMON_SERVER)
public interface SecretKeyFeignClient {

    /**
     *功能描述 :根据服务名获取秘钥(服务内部调用)
     * @author zht
     * @date 2019/12/23
     * @param serverName
     * @return com.y3tu.yao.common.model.entity.SecretKey
     */
    @GetMapping("/key/{server-name}")
    String getSecretKey(@PathVariable("server-name") String serverName);
}
