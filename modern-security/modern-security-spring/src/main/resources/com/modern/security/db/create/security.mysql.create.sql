create table sys_auth_user
(
    id                     bigint auto_increment comment '主键' primary key,
    username               varchar(127) not null default '' comment '用户登录名',
    password               varchar(127) not null default '' comment '用户密码',
    permissions            text null comment 'Token值',
    is_enabled             tinyint      not null default 1 comment '是否启用。1：启用，0：未启用',
    is_expired             tinyint      not null default 0 comment '账号是否过期。1：已经过期，0：未过期',
    is_locked              tinyint      not null default 0 comment '账号是否被锁。1：被锁，0：未被锁',
    is_credentials_expired tinyint      not null default 0 comment '用户的凭证是否过期。1：已经过期，0：未过期',
    creator_id             bigint       not null default -1 comment '记录生成人的ID',
    updater_id             bigint       not null default -1 comment '最后一个更新人的ID',
    create_time            datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time            datetime              default CURRENT_TIMESTAMP not null comment '修改时间'
) comment '认证用户信息' collate = utf8mb4_0900_ai_ci;

create table sys_auth_details
(
    id                  bigint auto_increment comment '主键' primary key,
    username            varchar(127) not null default '' comment '用户登录名',
    access_token        varchar(511) not null default '' comment '访问凭证Token',
    access_expire_time  bigint       not null default '' comment '访问凭证过期时间',
    refresh_token       varchar(511) not null default '' comment '访问凭证刷新Token',
    refresh_expire_time bigint       not null comment '访问凭证刷新过期时间'
) comment '存储有关身份验证请求的其他详细信息' collate = utf8mb4_0900_ai_ci;
