import Cookies from 'js-cookie'
import Config from '@/config'


const TokenKey = Config.TokenKey;
const RefreshTokenKey = Config.RefreshTokenKey;
const TokenExpireTime = Config.TokenExpireTime;

export function setToken(token, rememberMe) {
    if (rememberMe) {
        return Cookies.set(TokenKey, token, {expires: Config.tokenCookieExpires})
    } else {
        return Cookies.set(TokenKey, token)
    }
}

export function getToken() {
    return Cookies.get(TokenKey)
}

export function setTokenExpireTime(tokenExpireTime) {
    return Cookies.set(TokenExpireTime, tokenExpireTime)
}

export function getTokenExpireTime() {
    return Cookies.get(TokenExpireTime)
}

export function setRefreshToken(refreshToken) {
    return Cookies.set(RefreshTokenKey, refreshToken)
}

export function getRefreshToken() {
    return Cookies.get(RefreshTokenKey)
}

export function removeToken() {
    Cookies.remove(TokenKey);
    Cookies.remove(RefreshTokenKey);
}
