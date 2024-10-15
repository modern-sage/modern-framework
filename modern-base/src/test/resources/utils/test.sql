create table sys_user (
    id                     bigint auto_increment comment '主键' primary key,
    name                   varchar(127) not null default '' comment '姓名',
    code                   varchar(127) not null default '' comment '用户编码',
    username               varchar(127) not null default '' comment '用户登录名',
    password               varchar(127) not null default '' comment '用户密码',
    phone                  varchar(127) not null default '' comment '联系方式',
    email                  varchar(127) not null default '' comment '邮箱',
    perms                  text null comment '权限集合',
    is_enabled             tinyint      not null default 1 comment '是否启用。1：启用，0：未启用',
    is_expired             tinyint      not null default 0 comment '账号是否过期。1：已经过期，0：未过期',
    is_locked              tinyint      not null default 0 comment '账号是否被锁。1：被锁，0：未被锁',
    is_credentials_expired tinyint      not null default 0 comment '用户的凭证是否过期。1：已经过期，0：未过期',
    tenant_id              bigint null comment 'tenantId',
    memo                   text null comment '描述',
    creator_id             bigint       not null default -1 comment '记录生成人的ID',
    updater_id             bigint       not null default -1 comment '最后一个更新人的ID',
    create_time            datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time            datetime              default CURRENT_TIMESTAMP not null comment '修改时间',
    delete_flag            tinyint      not null default 0 comment '逻辑删除标志，状态1表示记录被删除，0表示正常记录',
    version                int          not null default 1 comment '版本号'
) comment '用户信息' collate = utf8mb4_0900_ai_ci;

INSERT INTO sys_user (`name`, `code`, `username`, `password`, `phone`, `email`, `perms`, `is_enabled`, `is_expired`, `is_locked`, `is_credentials_expired`, `tenant_id`, `memo`, `creator_id`, `updater_id`)
VALUES
    ('admin', 'admin', 'admin', '$2a$10$wZVcOPfPpCFqoXQwfPu35OnmUpL/buEegDlfwNVqWPDdkgSHy55Re',
     '', '', NULL, 1, 0, 0, 0, NULL, NULL, -1, -1);
