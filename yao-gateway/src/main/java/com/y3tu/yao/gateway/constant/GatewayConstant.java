package com.y3tu.yao.gateway.constant;

/**
 * ClassName: GatewayConstant
 * Description:
 * date: 2019/12/23 9:37
 *
 * @author zht
 */
public interface GatewayConstant {

    interface HeaderNames {
        String TIMESTAMP = "timestamp";
        String NONCE = "nonce";
        String SIGN = "sign";
        String SERVER_NAME = "server_name";
    }

    interface Active{
        String DEV = "dev";
        String TEST = "test";
        String PROD = "prod";
    }

}
