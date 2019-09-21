import {login, logout, getUserInfo, mobileLogin, refreshToken} from '@/api/login'
import {getToken, setToken, removeToken, getRefreshToken, setRefreshToken, setTokenExpireTime} from '@/utils/auth'

const user = {
    state: {
        token: getToken(),
        user: {},
        roles: [],
        // 第一次加载菜单时用到
        loadMenus: false
    },

    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_USER: (state, user) => {
            state.user = user
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        },
        SET_LOAD_MENUS: (state, loadMenus) => {
            state.loadMenus = loadMenus
        }
    },

    actions: {
        // 登录
        Login({commit}, userInfo) {
            const username = userInfo.username;
            const password = userInfo.password;
            const rememberMe = userInfo.rememberMe;
            const randomStr = userInfo.randomStr;
            const code = userInfo.code;
            return new Promise((resolve, reject) => {
                login(username, password, randomStr, code).then(res => {
                    saveLoginData(res);
                    commit('SET_TOKEN', res.access_token);
                    commit('SET_LOAD_MENUS', true);
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        RefreshToken({commit}) {
            return new Promise((resolve, reject) => {
                refreshToken(getRefreshToken()).then(res => {
                    saveLoginData(res);
                    commit('SET_TOKEN', res.access_token);
                    commit('SET_LOAD_MENUS', true);
                    resolve()
                }).catch(error => {
                    console.log(error);
                    reject(error)
                })
            })
        },

        //手机号验证码登录
        LoginByPhone({commit}, userInfo) {
            const mobile = userInfo.mobile.trim();
            const code = userInfo.code.trim();
            return new Promise((resolve, reject) => {
                mobileLogin(mobile, code).then(res => {
                    if (res.access_token) {
                        saveLoginData(res);
                        commit('SET_TOKEN', res.access_token);
                        commit('SET_LOAD_MENUS', true);
                        resolve()
                    }
                    resolve(res)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 获取用户信息
        GetUserInfo({commit}) {
            return new Promise((resolve, reject) => {
                getUserInfo().then(res => {
                    const data = res.data;
                    setUserInfo(data, commit);
                    resolve(res.data)
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 登出
        LogOut({commit, state}) {
            return new Promise((resolve, reject) => {
                logout({access_token: state.token}).then(() => {
                    commit('SET_TOKEN', '');
                    commit('SET_ROLES', []);
                    removeToken();
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 前端 登出
        FedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '');
                commit('SET_ROLES', []);
                removeToken()
                resolve()
            })
        },
        updateLoadMenus({commit}) {
            return new Promise((resolve, reject) => {
                commit('SET_LOAD_MENUS', false)
            })
        }
    }
};

/**
 * 保存用户登录token信息
 * @param res
 */
function saveLoginData(res) {
    setToken(res.access_token);
    setRefreshToken(res.refresh_token);
    const current = new Date();
    const expireTime = current.setTime(current.getTime() + 1000 * res.expires_in);
    setTokenExpireTime(expireTime);
}

export const setUserInfo = (user, commit) => {
    // 如果没有任何权限，则赋予一个默认的权限，避免请求死循环
    if (user.roles.length === 0) {
        commit('SET_ROLES', ['ROLE_SYSTEM_DEFAULT'])
    } else {
        commit('SET_ROLES', user.roles)
    }
    commit('SET_USER', user)
};

export default user
