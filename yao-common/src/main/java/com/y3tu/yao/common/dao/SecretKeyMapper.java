package com.y3tu.yao.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.y3tu.yao.common.model.entity.SecretKey;

/**
 * ClassName: SecretKeyMapper
 * Description:
 * date: 2019/12/23 17:09
 *
 * @author zht
 */
public interface SecretKeyMapper extends BaseMapper<SecretKey>{

    /**
     *功能描述 :根据服务名获取秘钥
     * @author zht
     * @date 2019/12/23
     * @param serverName
     * @return com.y3tu.yao.common.model.entity.SecretKey
     */
    SecretKey selectByServerName(String serverName);
}
