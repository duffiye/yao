import axios from 'axios'
import {MessageBox,Message} from 'element-ui'
import store from '@/store'
import {getToken, getRefreshToken, getTokenExpireTime} from '@/utils/auth'

// 创建axios实例
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,// api的base_url
    //withCredentials: true, // 跨域请求，允许保存cookie
    timeout: 10000, // 请求超时时间
    validateStatus(status) {
        return status === 200
    }
});

// request拦截器
service.interceptors.request.use(
    config => {
        let _config = config
        try {
            const expireTime = getTokenExpireTime();
            if (expireTime) {
                const left = expireTime - new Date().getTime()
                const refreshToken = getRefreshToken()
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
        console.log(error)
        return Promise.reject(error)
    }
);

// response拦截器
service.interceptors.response.use((config) => {
    return config
}, (error) => {
    if (error.response) {
        const errorMessage = error.response.data === null ? '系统内部异常，请联系网站管理员' : error.response.data.message;
        switch (error.response.status) {
            case 404:
                Message({
                    message: '很抱歉，资源未找到' || 'Error',
                    type: 'error',
                    duration: 5 * 1000
                })
                break
            case 403:
                Message({
                    message: '很抱歉，您暂无该操作权限' || 'Error',
                    type: 'error',
                    duration: 5 * 1000
                })
                break
            case 401:
                Message({
                    message: '很抱歉，认证已失效，请重新登录' || 'Error',
                    type: 'error',
                    duration: 5 * 1000
                })
                break
            default:
                if (errorMessage) {
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
