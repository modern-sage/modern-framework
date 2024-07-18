CREATE TABLE IF NOT EXISTS biz_001_dynamic_user
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    code                 VARCHAR(255) DEFAULT '',
    name                 VARCHAR(255) DEFAULT '',
    position             VARCHAR(255) DEFAULT '',
    dept                 VARCHAR(255) DEFAULT '',
    memo                 VARCHAR(255) DEFAULT '' NULL,
    status               INT DEFAULT 0 NULL,
    creator_id           BIGINT NULL,
    updater_id           BIGINT NULL,
    update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    create_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    version              INT DEFAULT 1 NOT NULL,
    delete_flag          INT DEFAULT 0 NOT NULL
);

insert into biz_001_dynamic_user(code, name) values ('001', '001');
insert into biz_001_dynamic_user(code, name) values ('002', '002');

CREATE TABLE IF NOT EXISTS biz_011_dynamic_user
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    code                 VARCHAR(255) DEFAULT '',
    name                 VARCHAR(255) DEFAULT '',
    position             VARCHAR(255) DEFAULT '',
    dept                 VARCHAR(255) DEFAULT '',
    memo                 VARCHAR(255) DEFAULT '' NULL,
    status               INT DEFAULT 0 NULL,
    creator_id           BIGINT NULL,
    updater_id           BIGINT NULL,
    update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    create_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    version              INT DEFAULT 1 NOT NULL,
    delete_flag          INT DEFAULT 0 NOT NULL
);

insert into biz_011_dynamic_user(code, name) values ('011', '011');
