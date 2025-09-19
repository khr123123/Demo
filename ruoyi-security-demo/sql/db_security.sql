/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : db_security

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 19/09/2025 10:02:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `parent_id` bigint NOT NULL COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 'SettingOutlined', 0, 1, '', '', 'M', NULL, '2025-09-19 08:53:10', '2025-09-19 08:57:33', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 'UserOutlined', 1, 1, '/sys/user/index', 'sys/user/index', 'C', 'system:user:query', '2025-09-19 08:53:10', '2025-09-19 09:47:01', '管理系统用户');
INSERT INTO `sys_menu` VALUES (3, '角色管理', 'TeamOutlined', 1, 2, '/sys/role/index', 'sys/role/index', 'C', 'system:role:query', '2025-09-19 08:53:10', '2025-09-19 09:47:01', '管理系统角色');
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 'MenuOutlined', 1, 3, '/sys/menu/index', 'sys/menu/index', 'C', 'system:menu:query', '2025-09-19 08:53:10', '2025-09-19 09:47:01', '管理系统菜单');
INSERT INTO `sys_menu` VALUES (5, '新增用户', NULL, 2, 1, '', '', 'F', 'system:user:modify', '2025-09-19 08:53:10', '2025-09-19 09:24:33', '新增用户按钮');
INSERT INTO `sys_menu` VALUES (6, '修改用户', NULL, 2, 2, '', '', 'F', 'system:user:modify', '2025-09-19 08:53:10', '2025-09-19 09:24:33', '修改用户按钮');
INSERT INTO `sys_menu` VALUES (7, '删除用户', NULL, 2, 3, '', '', 'F', 'system:user:modify', '2025-09-19 08:53:10', '2025-09-19 09:24:33', '删除用户按钮');
INSERT INTO `sys_menu` VALUES (8, '新增角色', NULL, 3, 1, '', '', 'F', 'system:role:add', '2025-09-19 08:53:10', '2025-09-19 09:21:01', '新增角色按钮');
INSERT INTO `sys_menu` VALUES (9, '修改角色', NULL, 3, 2, '', '', 'F', 'system:role:edit', '2025-09-19 08:53:10', '2025-09-19 09:21:01', '修改角色按钮');
INSERT INTO `sys_menu` VALUES (10, '删除角色', NULL, 3, 3, '', '', 'F', 'system:role:delete', '2025-09-19 08:53:10', '2025-09-19 09:21:01', '删除角色按钮');
INSERT INTO `sys_menu` VALUES (11, '新增菜单', NULL, 4, 1, '', '', 'F', 'system:menu:add', '2025-09-19 08:53:10', '2025-09-19 09:22:01', '新增菜单按钮');
INSERT INTO `sys_menu` VALUES (12, '修改菜单', NULL, 4, 2, '', '', 'F', 'system:menu:edit', '2025-09-19 08:53:10', '2025-09-19 09:22:01', '修改菜单按钮');
INSERT INTO `sys_menu` VALUES (13, '删除菜单', NULL, 4, 3, '', '', 'F', 'system:menu:delete', '2025-09-19 08:53:10', '2025-09-19 09:22:01', '删除菜单按钮');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色权限字符串',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '系统管理员', 'admin', '2025-09-19 08:53:11', '2025-09-19 08:53:11', '拥有系统全部权限');
INSERT INTO `sys_role` VALUES (2, '普通用户', 'user', '2025-09-19 08:53:11', '2025-09-19 08:53:11', '仅能查看和编辑个人信息');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色菜单主键ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_role_menu_role`(`role_id` ASC) USING BTREE,
  INDEX `fk_role_menu_menu`(`menu_id` ASC) USING BTREE,
  CONSTRAINT `fk_role_menu_menu` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_menu_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1, 1);
INSERT INTO `sys_role_menu` VALUES (2, 1, 2);
INSERT INTO `sys_role_menu` VALUES (3, 1, 3);
INSERT INTO `sys_role_menu` VALUES (4, 1, 4);
INSERT INTO `sys_role_menu` VALUES (5, 1, 5);
INSERT INTO `sys_role_menu` VALUES (6, 1, 6);
INSERT INTO `sys_role_menu` VALUES (7, 1, 7);
INSERT INTO `sys_role_menu` VALUES (8, 1, 8);
INSERT INTO `sys_role_menu` VALUES (9, 1, 9);
INSERT INTO `sys_role_menu` VALUES (10, 1, 10);
INSERT INTO `sys_role_menu` VALUES (11, 1, 11);
INSERT INTO `sys_role_menu` VALUES (12, 1, 12);
INSERT INTO `sys_role_menu` VALUES (13, 1, 13);
INSERT INTO `sys_role_menu` VALUES (14, 2, 1);
INSERT INTO `sys_role_menu` VALUES (15, 2, 2);
INSERT INTO `sys_role_menu` VALUES (16, 2, 5);
INSERT INTO `sys_role_menu` VALUES (17, 2, 6);
INSERT INTO `sys_role_menu` VALUES (18, 2, 7);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phonenumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'https://i.pravatar.cc/150?img=1', 'admin@example.com', '13800000000', '2025-09-19 08:47:22', '0', '2025-09-19 08:53:11', '2025-09-19 08:55:02', '超级管理员');
INSERT INTO `sys_user` VALUES (2, 'zhangsan', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'https://i.pravatar.cc/150?img=2', 'zhangsan@example.com', '13800000001', '2025-09-19 08:53:11', '0', '2025-09-19 08:53:11', '2025-09-19 08:55:02', '普通用户');
INSERT INTO `sys_user` VALUES (3, 'lisi', '$2a$10$J5iFVMl3dW5h66ToRG9Su.vV8jNQWdJvgaUg8dqrYmQpp563Olsye', 'https://i.pravatar.cc/150?img=3', 'lisi@example.com', '13800000002', '2025-09-19 08:53:11', '1', '2025-09-19 08:53:11', '2025-09-19 08:55:02', '停用用户');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户角色主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_role_user`(`user_id` ASC) USING BTREE,
  INDEX `fk_user_role_role`(`role_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 2);
INSERT INTO `sys_user_role` VALUES (3, 3, 2);

SET FOREIGN_KEY_CHECKS = 1;
