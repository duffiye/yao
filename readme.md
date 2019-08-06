# Yao 
>基于Spring Cloud Alibaba、Oauth2、Vue的快速开发脚手架，持续开发中...

## 技术栈
- 注册中心：Nacos
- 服务网关：Gateway
- 配置中心：Nacos
- 服务调用：OpenFeign
- 负载均衡：Loadbalancer
- 熔断降级：Sentinel
- 链路追踪：Skywalking
- 消息队列：RabbitMQ
- 权限认证：Oauth2
- 项目部署：Docker
- 前端框架：Vue ElementUI

## 项目结构说明
- yao-common 公共模块
- yao-auth  Oauth2认证服务 提供token
  >yao-authentication 签权服务   
  >yao-authorization 授权服务
- yao-back 后台管理模块 
  >代码生成、upms(通用权限管理)、oa(流程办公 todo)、cms(内容管理 todo) ...
- yao-log 日志中心模块
  >yao-log-sever 日志服务   
  >yao-log-starter 日志starter
- yao-gateway 后端统一入口，提供动态路由
- yao-ui 基于Vue ElementUI的后台管理界面，可打包进jar包，也可单独部署

## 功能模块
```
√ 用户管理 
√ 资源管理(菜单和权限) 
√ 角色管理 
√ 部门管理 
√ 字典管理
√ 代码生成
```


## 默认端口
```
yao-gateway:2019
yao-back:9001
yao-authentication:9002
yao-authorization:9003
yao-log:8999
```

## 编译部署
* 执行`run.sh` 编译打包服务生成镜像并上传docker hub
* 执行`deploy.sh`远程调用服务器拉取镜像并启动服务
> server.txt里面存储的是远程服务器的ip、端口、用户名、密码、服务名、服务端口
