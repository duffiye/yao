create table if not exists t_log
(
    id          varchar(255)                           not null comment '主键' primary key,
    status      char         default '0'               not null comment '操作状态  0 成功 1 失败',
    module_name varchar(255) default ''                null comment '模块名',
    action_name varchar(255) default ''                null comment '操作名',
    server_name varchar(32)                            null comment '服务名',
    remote_addr varchar(255)                           null comment '操作IP地址',
    user_agent  varchar(1000)                          null comment '用户代理',
    request_uri varchar(255)                           null comment '请求URI',
    method      varchar(10)                            null comment '操作方式',
    params      text                                   null comment '操作提交的数据',
    time        mediumtext                             null comment '执行时间',
    exception   text                                   null comment '异常信息',
    del_flag    char         default '0'               null comment '删除标记',
    username    varchar(64)                            null comment '调用人',
    create_time datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    action_type varchar(50)                            null comment '操作类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '日志表';
create index log_create_by on t_log (username);
create index log_create_date on t_log (create_time);
create index log_request_uri on t_log (request_uri);

