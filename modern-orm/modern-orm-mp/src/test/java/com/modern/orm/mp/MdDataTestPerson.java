package com.modern.orm.mp;


import lombok.Data;

/**
 * 人员测试类
 */
@Data
public class MdDataTestPerson extends BaseBizPo<MdDataTestPerson> {

    public final static String CREATE_SQL_MYSQL = "create table md_data_test_person\n" +
            "(\n" +
            "    id                   bigint primary key auto_increment,\n" +
            "    code                 varchar(255) default '',\n" +
            "    name                 varchar(255) default '',\n" +
            "    position             varchar(255) default '',\n" +
            "    dept                 varchar(255) default '',\n" +
            "    memo                 varchar(255) default ''                null comment '描述',\n" +
            "    status               int          default 0                 null comment 'status',\n" +
            "    creator_id              bigint                                 null,\n" +
            "    updater_id              bigint                                 null,\n" +
            "    update_time          timestamp    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,\n" +
            "    create_time          timestamp    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,\n" +
            "    version              int          default 1                 not null comment '版本号',\n" +
            "    delete_flag          int          default 0                 not null comment '是否删除'\n" +
            ");\n";

    public final static String CREATE_SQL = "CREATE TABLE md_data_test_person\n" +
            "(\n" +
            "    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    code                 VARCHAR(255) DEFAULT '',\n" +
            "    name                 VARCHAR(255) DEFAULT '',\n" +
            "    position             VARCHAR(255) DEFAULT '',\n" +
            "    dept                 VARCHAR(255) DEFAULT '',\n" +
            "    memo                 VARCHAR(255) DEFAULT '' NULL,\n" +
            "    status               INT DEFAULT 0 NULL,\n" +
            "    creator_id           BIGINT NULL,\n" +
            "    updater_id           BIGINT NULL,\n" +
            "    update_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,\n" +
            "    create_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,\n" +
            "    version              INT DEFAULT 1 NOT NULL,\n" +
            "    delete_flag          INT DEFAULT 0 NOT NULL\n" +
            ");";

    public final static String DROP_SQL = "drop table if exists md_data_test_person;";

    private String position;
    private String dept;

}
