<template>
    <div class="login">
        <el-row type="flex" justify="center" @keydown.enter.native="submitLogin">
            <el-col style="width: 368px;">
                <el-row class="header">
                    <img :src="logo" alt="logo" width="220px">
                    <div class="description">快速开发脚手架</div>
                </el-row>
                <el-row>
                    <el-tabs v-model="tabName">
                        <el-tab-pane name="username">
                            <span slot="label"><i class="el-icon-user"></i> 用户密码登录</span>
                            <el-card shadow="never">
                                <el-form v-if="tabName==='username'"
                                         ref="loginForm"
                                         label-position="top"
                                         :rules="loginRules"
                                         :model="loginForm"
                                         size="default">
                                    <el-form-item prop="username">
                                        <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名">
                                            <i slot="prepend" class="el-icon-s-custom"></i>
                                        </el-input>
                                    </el-form-item>
                                    <el-form-item prop="password">
                                        <el-input type="password" v-model="loginForm.password" placeholder="请输入密码">
                                            <i slot="prepend" class="el-icon-lock"></i>
                                        </el-input>
                                    </el-form-item>
                                    <el-form-item prop="code">
                                        <el-input type="text" v-model="loginForm.code" placeholder="请输入验证码">
                                            <template slot="prepend">验证码</template>
                                            <template slot="append">
                                                <img class="login-code" :src="code.src" @click="refreshCode" alt="">
                                            </template>
                                        </el-input>
                                    </el-form-item>
                                </el-form>
                            </el-card>
                        </el-tab-pane>

                        <el-tab-pane label="手机号登录" name="mobile">
                            <span slot="label"><i class="el-icon-mobile-phone"></i> 手机号登录</span>
                            <el-card shadow="never">
                                <el-form v-if="tabName==='mobile'"
                                         ref="mobileForm"
                                         label-position="top"
                                         :rules="mobileRules"
                                         :model="mobileForm"
                                         size="default">
                                    <el-form-item prop="mobile">
                                        <el-input type="text" v-model="mobileForm.mobile" placeholder="请输入手机号">
                                            <i slot="prepend" class="fa fa-mobile" style="font-size:22px"></i>
                                        </el-input>
                                    </el-form-item>
                                    <el-form-item prop="code">
                                        <el-input type="text" v-model="mobileForm.code" placeholder="请输入6位验证码">
                                            <template slot="append">
                                                <el-button @click="sendMsg" :disabled="sms.isDisabled">{{sms.btnName}}
                                                </el-button>
                                            </template>
                                        </el-input>
                                    </el-form-item>
                                </el-form>
                            </el-card>
                        </el-tab-pane>
                    </el-tabs>
                </el-row>
                <el-row>
                    <el-button :loading="loading"
                               size="medium"
                               type="primary"
                               style="width:100%;"
                               @click="submitLogin">
                        <span v-if="!loading">登 录</span>
                        <span v-else>登 录 中...</span>
                    </el-button>
                </el-row>
                <el-row type="flex"
                        justify="center"
                        style="position: fixed;bottom: 4vh;width: 368px;color: rgba(0, 0, 0, 0.45);font-size: 14px;">
                    Copyright © 2019 - Present<a href="http://www.y3tu.com" target="_blank"
                                                 style="margin:0 5px;">Y3tu</a>
                </el-row>
            </el-col>
        </el-row>


    </div>
</template>

<script>
    import Cookies from 'js-cookie'
    import {randomLenNum} from '@/utils/util'
    import Config from '@/config'
    import {sendMobileCode} from '@/api/login'
    import logo from '@/assets/logo/logo.png'
    import './style.scss'

    export default {
        name: 'Login',
        data() {
            return {
                logo: logo,
                tabName: 'username',
                loginForm: {
                    username: 'admin',
                    password: 'admin',
                    code: '',
                    randomStr: '',
                    rememberMe: false
                },
                loginRules: {
                    username: [{required: true, trigger: 'blur', message: '用户名不能为空'}],
                    password: [{required: true, trigger: 'blur', message: '密码不能为空'}],
                    code: [{required: true, trigger: 'blur', message: '验证码不能为空'}]
                },
                code: {
                    len: 4,
                    src: ''
                },
                mobileForm: {
                    mobile: '',
                    code: ''
                },
                mobileRules: {
                    mobile: [
                        {required: true, message: '请输入手机号', trigger: 'blur'},
                        {
                            pattern: /^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/,
                            message: '手机号不合法',
                            trigger: 'blur'
                        }
                    ],
                    code: [
                        {required: true, message: '请输入验证码', trigger: 'blur'}
                    ]
                },
                sms: {
                    btnName: '发送短信',
                    isDisabled: false,
                    time: 60
                },
                loading: false,
                redirect: undefined
            }
        },
        watch: {
            $route: {
                handler: function (route) {
                    this.redirect = route.query && route.query.redirect
                },
                immediate: true
            }
        },
        created() {
            this.getCookie()
            this.$nextTick(() => {
                this.refreshCode()
            })
        },
        mounted () {
        },
        methods: {
            getCookie() {
                const username = Cookies.get('username')
                let password = Cookies.get('password')
                const rememberMe = Cookies.get('rememberMe')
                password = password === undefined ? this.loginForm.password : password
                this.loginForm = {
                    username: username === undefined ? this.loginForm.username : username,
                    password: password,
                    rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
                }
            },
            /**
             * 刷新验证码
             */
            refreshCode() {
                //this.loginForm.code = '';
                this.loginForm.randomStr = randomLenNum(this.code.len, true);
                this.code.src = Config.baseURL + `/code?randomStr=${this.loginForm.randomStr}`
            },
            submitLogin() {
                if (this.tabName === "username") {
                    //用户名密码登录
                    this.$refs.loginForm.validate(valid => {
                        const user = this.loginForm;
                        if (valid) {
                            this.loading = true;
                            if (user.rememberMe) {
                                Cookies.set('username', user.username, {expires: Config.passCookieExpires});
                                Cookies.set('password', user.password, {expires: Config.passCookieExpires});
                                Cookies.set('rememberMe', user.rememberMe, {expires: Config.passCookieExpires})
                            } else {
                                Cookies.remove('username');
                                Cookies.remove('password');
                                Cookies.remove('rememberMe')
                            }
                            this.$store.dispatch('Login', user).then(() => {
                                this.loading = false;
                                this.$router.push({path: this.redirect || '/'})
                            }).catch(() => {
                                this.loading = false
                            })
                        } else {
                            console.log('error submit!!')
                            return false
                        }
                    })
                } else if (this.tabName === "mobile") {
                    //手机号登录
                    this.$refs.mobileForm.validate(valid => {
                        if (valid) {
                            this.$store
                                .dispatch('LoginByPhone', this.mobileForm)
                                .then((rep) => {
                                    console.log(rep)
                                    if (rep.access_token) {
                                        this.loading = false;
                                        this.$router.push({
                                            path: '/'
                                        })
                                    } else {
                                        this.$message.error(rep.data)
                                    }
                                }).catch(() => {
                                this.loading = false
                            })
                        } else {
                            console.log('error submit!!')
                            return false
                        }
                    })
                }
            },
            //发送短信
            sendMsg() {
                let me = this;
                me.$refs.mobileForm.validateField('mobile', (error) => {
                    if (!error) {
                        sendMobileCode(me.mobileForm.mobile)
                            .then(({data}) => {
                                if (data.status === 'SUCCEED') {
                                    this.$notify({
                                        title: '验证码发送成功',
                                        message: '验证码为:' + data.result,
                                        type: 'success',
                                        duration: 6000
                                    });
                                    doInterval()
                                }
                            })
                    }
                });

                function doInterval() {
                    me.sms.isDisabled = true;
                    let interval = window.setInterval(function () {
                        me.sms.btnName = '（' + me.sms.time + '秒）后重新发送';
                        --me.sms.time;
                        if (me.sms.time < 0) {
                            me.sms.btnName = '重新发送';
                            me.sms.time = 10;
                            me.sms.isDisabled = false;
                            window.clearInterval(interval)
                        }
                    }, 1000)
                }
            },
        }
    }
</script>

