package com.y3tu.yao.common.controller;

import com.y3tu.yao.common.service.SecretKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: SecretKeyController
 * Description:
 * date: 2019/12/23 16:58
 *
 * @author zht
 */
@RestController
@RequestMapping("/key")
public class SecretKeyController {

    @Autowired
    SecretKeyService secretKeyService;


    /**
     *功能描述 :根据服务名获取秘钥(服务内部调用)
     * @author zht
     * @date 2019/12/23
     * @param serverName
     * @return com.y3tu.yao.common.model.entity.SecretKey
     */
    @GetMapping("/{server-name}")
    public String getSecretKey(@PathVariable("server-name") String serverName){
        return secretKeyService.getSecretKey(serverName).getSecretKey();
    }
}
