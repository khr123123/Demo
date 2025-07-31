create database `permission_demo`;
use `permission_demo`;
-- 用户表
CREATE TABLE `sys_user`
(
    `id`          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    `username`    VARCHAR(50)  NOT NULL UNIQUE COMMENT '用户名',
    `password`    VARCHAR(100) NOT NULL COMMENT '密码',
    `status`      TINYINT  DEFAULT 1 COMMENT '状态（1正常 0停用）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

-- 角色表
CREATE TABLE `sys_role`
(
    `id`          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    `role_key`    VARCHAR(50) NOT NULL UNIQUE COMMENT '角色标识',
    `role_name`   VARCHAR(50) NOT NULL COMMENT '角色名称',
    `status`      TINYINT  DEFAULT 1 COMMENT '状态',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

-- 权限表（
CREATE TABLE `sys_permission`
(
    `id`             BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    `permission_key` VARCHAR(100) NOT NULL UNIQUE COMMENT '权限标识（如 system:user:list）',
    `url`            VARCHAR(200) COMMENT '接口路径',
    `status`         TINYINT  DEFAULT 1 COMMENT '状态',
    `create_time`    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

-- 用户与角色关联表
CREATE TABLE `sys_user_role`
(
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
);

-- 角色与权限关联表
CREATE TABLE `sys_role_permission`
(
    `role_id`       BIGINT NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`role_id`, `permission_id`)
);
