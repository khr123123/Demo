create database if not exists `db_security`;
use db_security;
-- 菜单表
CREATE TABLE sys_menu
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '菜单主键ID',
    name        VARCHAR(50) NOT NULL COMMENT '菜单名称',
    icon        VARCHAR(100) DEFAULT NULL COMMENT '菜单图标',
    parent_id   BIGINT      NOT NULL COMMENT '父菜单ID',
    order_num   INT          DEFAULT 0 COMMENT '显示顺序',
    path        VARCHAR(200) DEFAULT NULL COMMENT '路由地址',
    component   VARCHAR(200) DEFAULT NULL COMMENT '组件路径',
    menu_type   CHAR(1)     NOT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
    perms       VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark      VARCHAR(200) DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单表';


-- 角色表
CREATE TABLE sys_role
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色主键ID',
    name        VARCHAR(50) NOT NULL COMMENT '角色名称',
    code        VARCHAR(50) NOT NULL COMMENT '角色权限字符串',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark      VARCHAR(200) DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色表';


-- 角色菜单关联表
CREATE TABLE sys_role_menu
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色菜单主键ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    CONSTRAINT fk_role_menu_role FOREIGN KEY (role_id) REFERENCES sys_role (id),
    CONSTRAINT fk_role_menu_menu FOREIGN KEY (menu_id) REFERENCES sys_menu (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色菜单关联表';


-- 用户表
CREATE TABLE sys_user
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username    VARCHAR(50)  NOT NULL COMMENT '用户名',
    password    VARCHAR(100) NOT NULL COMMENT '密码',
    avatar      VARCHAR(255) DEFAULT NULL COMMENT '用户头像',
    email       VARCHAR(100) DEFAULT NULL COMMENT '用户邮箱',
    phonenumber VARCHAR(20)  DEFAULT NULL COMMENT '手机号码',
    login_date  DATETIME     DEFAULT NULL COMMENT '最后登录时间',
    status      CHAR(1)      DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark      VARCHAR(200) DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';


-- 用户角色关联表
CREATE TABLE sys_user_role
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户角色主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES sys_user (id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES sys_role (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关联表';
