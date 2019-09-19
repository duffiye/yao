/**
 * 系统全局配置
 */
export default {
    /**
     * 记住密码状态下的token在Cookie中存储的天数，默认1天
     */
    tokenCookieExpires: 1,
    /**
     * 记住密码状态下的密码在Cookie中存储的天数，默认1天
     */
    passCookieExpires: 1,
    /**
     * 此处修改网站名称
     */
    webName: 'Yao',
    /**
     * token key
     */
    TokenKey: 'YAO-TOKEN-KEY',
    /**
     * refreshToken key
     */
    RefreshTokenKey: 'YAO-REFRESH-TOKEN-KEY',
    /**
     * token失效时间
     */
    TokenExpireTime:'YAO-TOKEN-EXPIRE-TIME',
    /**
     * 是否显示 tagsView
     */
    tagsView: true,

    /**
     * 固定头部
     */
    fixedHeader: false,

    /**
     * 是否显示logo
     */
    sidebarLogo: true,

    /**
     * 是否显示设置的悬浮按钮
     */
    settingBtn: true
}
