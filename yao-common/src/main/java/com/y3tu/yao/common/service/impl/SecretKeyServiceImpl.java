package com.y3tu.yao.common.service.impl;

import com.y3tu.yao.common.dao.SecretKeyMapper;
import com.y3tu.yao.common.service.SecretKeyService;
import com.y3tu.yao.common.model.entity.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: SecretKeyServiceImpl
 * Description:
 * date: 2019/12/23 17:08
 *
 * @author zht
 */
@Service
public class SecretKeyServiceImpl  implements SecretKeyService{
    @Autowired
    private SecretKeyMapper secretKeyMapper;

    @Override
    public SecretKey getSecretKey(String serverName) {
        return secretKeyMapper.selectByServerName(serverName);
    }
}
