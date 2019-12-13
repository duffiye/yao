package com.y3tu.yao.common.constants;

/**
 * 安全配置常量
 *
 * @author y3tu
 * @date 2019-05-03
 */
public interface AuthConstants {
    /**
     * token的header key
     */
    String TOKEN_HEADER = "Authorization";

    String CLOUD_PREFIX = "yao-";
    /**
     * 项目的license
     */
    String LICENSE = "made by y3tu";

    /**
     * 验证码redis key
     */
    String VALIDATE_CODE_KEY = "VALIDATE_CODE_KEY";
    /**
     * 验证码类型 gif类型
     */
    String VALIDATE_CODE_TYPE_GIF = "gif";
    /**
     * 验证码类型 png类型
     */
    String VALIDATE_CODE_TYPE_PNG = "png";
    /**
     * jwt 加密key
     */
    String SIGN_KEY = "123456";

    /**
     * sys_oauth_client_details 表的字段，不包括client_id、client_secret
     */
    String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService 查询语句
     */
    String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS + " from t_oauth_client_details";

    /**
     * 默认的查询语句
     */
    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    /**
     * 按条件client_id 查询
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    /**
     * 手机验证码登录的地址
     */
    String MOBILE_TOKEN_URL = "/mobile/token";
    /**
     * 登录获取token路径
     * oauth token
     */
    String OAUTH_TOKEN_URL = "/oauth/token";

    /**
     * 刷新token路径
     * grant_type
     */
    String REFRESH_TOKEN = "refresh_token";
    /**
     * 手机验证码redis存储前缀
     */
    String REDIS_MOBILE_CODE_PREFIX = "yao-mobile-code-";
    /**
     * 手机验证码redis失效时间，单位秒
     */
    Integer REDIS_MOBILE_CODE_EXPIRE = 60;

}
