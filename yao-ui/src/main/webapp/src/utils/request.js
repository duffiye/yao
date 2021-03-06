import axios from 'axios'
import {MessageBox,Message} from 'element-ui'
import store from '@/store'
import {getToken, getRefreshToken, getTokenExpireTime} from '@/utils/auth'
import {isNotEmpty} from '@/utils/validate'

// 创建axios实例
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,// api的base_url
    //withCredentials: true, // 跨域请求，允许保存cookie
    timeout: 60000, // 请求超时时间
    validateStatus(status) {
        return status === 200
    }
});

// request拦截器
service.interceptors.request.use(
    config => {
        let _config = config;
        //如果是请求或者刷新token请求直接放行
        if(config.url==="/token/oauth/token"){
            return _config;
        }
        try {
            const expireTime = getTokenExpireTime();
            if (expireTime) {
                const left = expireTime - new Date().getTime();
                const refreshToken = getRefreshToken();
                if (left < 5 * 60 * 1000 && refreshToken) {
                    _config = queryRefreshToken(_config, refreshToken)
                } else {
                    if (getToken()) {
                        _config.headers['Authorization'] = 'bearer ' + getToken()
                    }
                }
            }
        } catch (e) {
            console.error(e)
        }
        return _config
    },
    error => {
        console.log(error);
        return Promise.reject(error)
    }
);

// response拦截器
service.interceptors.response.use( response => {
    if (response.data.status === "ERROR") {
        Message({
            message: response.data.message,
            type: 'error',
            duration: 5 * 1000
        });
        throw new Error(response.data.message);
    }

    if (response.data.status === 'WARN') {
        Message({
            message: response.data.message,
            type: 'warning',
            duration: 5 * 1000
        });
        throw new Error(response.data.message);
    }
    return response.data;
}, (error) => {
    if (error.toString().indexOf('Error: timeout') !== -1) {
        Message({
            message: '网络请求超时',
            type: 'error',
            duration: 5 * 1000
        });
        return Promise.reject(error)
    }
    if (error.toString().indexOf('Error: Network Error') !== -1) {
        Message({
            message: '网络请求错误',
            type: 'error',
            duration: 5 * 1000
        });
        return Promise.reject(error)
    }
    if (error.toString().indexOf('503') !== -1) {
        Message({
            message: '服务暂时不可用，请稍后再试!' || 'Error',
            type: 'error',
            duration: 5 * 1000
        });
        return Promise.reject(error)
    }

    if (error.response) {
        const errorMessage = error.response.data === null ? '系统内部异常，请联系网站管理员' : error.response.data.message;
        switch (error.response.status) {
            case 404:
                Message({
                    message: '很抱歉，资源未找到',
                    type: 'error',
                    duration: 5 * 1000
                });
                break;
            case 403:
                Message({
                    message: '很抱歉，您暂无该操作权限',
                    type: 'error',
                    duration: 5 * 1000
                });
                break;
            case 401:
                Message({
                    message: isNotEmpty(errorMessage)?errorMessage:'很抱歉，用户名密码错误或者认证已失效，请重新登录',
                    type: 'error',
                    duration: 5 * 1000
                });
                break;
            default:
                if (errorMessage&&errorMessage!=='无效token') {
                    Message({
                        message: errorMessage,
                        type: 'error',
                        duration: 5 * 1000
                    })
                }
                break
        }
    }
    return Promise.reject(error)
});

function queryRefreshToken(config) {
    //如果是token过期，首先用refreshToken去刷新token
    store.dispatch('RefreshToken').then(() => {
        config.headers.Authorization = 'Bearer ' + getToken();
        return config
    }).catch(error => {
        //跳转到登录页面
        MessageBox.confirm(
            '登录状态已过期，您可以继续留在该页面，或者重新登录',
            '系统提示',
            {
                confirmButtonText: '重新登录',
                cancelButtonText: '取消',
                type: 'warning'
            }
        ).then(() => {
            store.dispatch('FedLogOut').then(() => {
                location.reload();
            })
        });
    });
}

export default service
