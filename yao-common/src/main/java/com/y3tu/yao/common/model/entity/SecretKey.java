package com.y3tu.yao.common.model.entity;

import lombok.Data;

/**
 * ClassName: SecretKey
 * Description:
 * date: 2019/12/23 17:01
 *
 * @author zht
 */
@Data
public class SecretKey {
    private Long id;

    private String secretKey;

    private String serverName;
}
