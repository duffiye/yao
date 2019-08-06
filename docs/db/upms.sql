create table if not exists generator_config
(
    id          bigint auto_increment comment 'ID' primary key,
    author      varchar(255) null comment '作者',
    module_name varchar(255) null comment '模块名称',
    pack        varchar(255) null comment '至于哪个包下',
    prefix      varchar(255) null comment '表前缀',
    api_prefix  varchar(255) null comment '前端请求路径前缀'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '代码生成配置表';

create table if not exists t_department
(
    id          varchar(255)   not null comment '主键' primary key,
    create_by   varchar(255)   null comment '创建人',
    create_time datetime       null comment '创建时间',
    del_flag    int            null comment '删除标志',
    update_by   varchar(255)   null comment '更新人',
    update_time datetime       null comment '更新时间',
    parent_id   varchar(255)   null comment '父部门id',
    sort        decimal(10, 2) null comment '排序',
    status      int            null comment '状态',
    name        varchar(255)   null comment '部门名称'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '部门表';

create table if not exists t_resource
(
    id          varchar(255)   not null primary key comment '主键',
    create_by   varchar(255)   null comment '创建人',
    create_time datetime       null comment '创建时间',
    del_flag    int            null comment '删除标志',
    update_by   varchar(255)   null comment '更新人',
    update_time datetime       null comment '更新时间',
    description varchar(255)   null comment '描述',
    name        varchar(255)   null comment '资源名称',
    parent_id   varchar(255)   null comment '父资源id',
    type        int            null comment '资源类型',
    sort        decimal(10, 2) null comment '排序',
    component   varchar(255)   null comment '菜单页面组件',
    path        varchar(255)   null comment '菜单页面文件路径',
    icon        varchar(255)   null comment '图标',
    permission  varchar(20)    null comment '权限',
    button_type varchar(255)   null comment '按钮类型',
    status      int            null comment '状态',
    url         varchar(255)   null comment '第三方url'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '资源菜单权限表';

create table if not exists t_role
(
    id           varchar(255) not null primary key,
    create_by    varchar(255) null comment '创建人',
    create_time  datetime     null comment '创建时间',
    del_flag     int          null comment '删除标志',
    update_by    varchar(255) null comment '更新人',
    update_time  datetime     null comment '更新时间',
    description  varchar(255) null comment '描述',
    name         varchar(255) null comment '角色名称',
    default_role bit          null comment '默认角色',
    data_type    int          null comment '数据类型',
    role_code    varchar(20)  null comment '角色编码',
    constraint t_role_code_uindex unique (role_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '角色表';

create table if not exists t_role_department
(
    id            varchar(255) not null primary key,
    create_by     varchar(255) null comment '创建人',
    create_time   datetime     null comment '创建时间',
    del_flag      int          null comment '删除标志',
    update_by     varchar(255) null comment '更新人',
    update_time   datetime     null comment '更新时间',
    department_id varchar(255) null comment '部门id',
    role_id       varchar(255) null comment '角色id'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '角色部门关联表';

create table if not exists t_role_resource
(
    id          varchar(255) not null primary key,
    create_by   varchar(255) null comment '创建人',
    create_time datetime     null comment '创建时间',
    del_flag    int          null comment '删除标志',
    update_by   varchar(255) null comment '更新人',
    update_time datetime     null comment '更新时间',
    resource_id varchar(255) null comment '资源id',
    role_id     varchar(255) null comment '角色id'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '角色资源关联表';

create table if not exists t_user
(
    id            varchar(255)    not null primary key,
    create_by     varchar(255)    null comment '创建人',
    create_time   datetime        null comment '创建时间',
    del_flag      int             null comment '删除标志',
    update_by     varchar(255)    null comment '更新人',
    update_time   datetime        null comment '更新时间',
    description   varchar(255)    null comment '描述',
    address       varchar(255)    null comment '地址',
    avatar        varchar(1000)   null comment '头像',
    email         varchar(255)    null comment '邮箱',
    mobile        varchar(255)    null comment '手机号码',
    nick_name     varchar(255)    null comment '昵称',
    password      varchar(255)    null comment '密码',
    sex           int             null comment '性别',
    status        int default '0' null comment '0:有效-1:禁用',
    username      varchar(255)    null comment '登录用户名',
    department_id varchar(255)    null comment '部门id'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户表';

create table if not exists t_user_role
(
    id          varchar(255) not null primary key,
    create_by   varchar(255) null comment '创建人',
    create_time datetime     null comment '创建时间',
    del_flag    int          null comment '删除标志',
    update_by   varchar(255) null comment '更新人',
    update_time datetime     null comment '更新时间',
    role_id     varchar(255) null comment '角色id',
    user_id     varchar(255) null comment '用户id'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '用户角色关联表';

create table if not exists t_dict
(
    id          varchar(255)       not null primary key,
    create_by   varchar(255)       null comment '创建人',
    create_time datetime           null comment '创建时间',
    del_flag    int                null comment '删除标志',
    update_by   varchar(255)       null comment '更新人',
    update_time datetime           null comment '更新时间',
    description varchar(255)       null comment '描述',
    name        varchar(255)       null comment '字典名称',
    code        varchar(255)       null comment '字典编码',
    type        int(1) default '0' null comment '字段分类 0：普通字典 1：sql字典',
    constraint t_dict_code_uindex unique (code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '字典表';

create table if not exists t_dict_data
(
    id          varchar(255)   not null primary key,
    create_by   varchar(255)   null comment '创建人',
    create_time datetime       null comment '创建时间',
    del_flag    int            null comment '删除标志',
    update_by   varchar(255)   null comment '更新人',
    update_time datetime       null comment '更新时间',
    description varchar(255)   null comment '描述',
    dict_id     varchar(255)   null comment 't_dict表主键',
    sort        decimal(10, 2) null comment '排序',
    status      int            null comment '状态',
    name        varchar(255)   null comment '名称',
    value       varchar(255)   null comment '值'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '字典详细数据表';

create table if not exists t_dict_sql
(
    id            varchar(255)  not null comment '主键' primary key,
    name          varchar(255)  null comment '名称',
    sql_text      varchar(4000) null comment 'sql',
    value_col     varchar(255)  null comment '值-字段名',
    name_col      varchar(255)  null comment '值对应的名字-字段名',
    max_rows      int           null comment '最大数据量',
    datasource_id varchar(255)  null comment 'data_source表主键',
    dict_id       varchar(255)  null comment 't_dict表主键',
    status        int           null comment '状态',
    description   varchar(255)  null comment '描述',
    create_by     varchar(255)  null comment '创建人',
    create_time   datetime      null comment '创建时间',
    del_flag      int           null comment '删除标志',
    update_by     varchar(255)  null comment '更新人',
    update_time   datetime      null comment '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 comment '字典sql数据表';









