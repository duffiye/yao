package com.y3tu.yao.common.service;

import com.y3tu.yao.common.model.entity.SecretKey;

/**
 * ClassName: SecretKeyService
 * Description:
 * date: 2019/12/23 17:06
 *
 * @author zht
 */
public interface SecretKeyService {

    /**
     *功能描述 :根据服务名获取秘钥
     * @author zht
     * @date 2019/12/23
     * @param serverName
     * @return com.y3tu.yao.common.model.entity.SecretKey
     */
    SecretKey getSecretKey(String serverName);
}
