-- 插入用户
INSERT INTO sys_user (id, username, password, status, create_time)
VALUES (1, 'admin', 'admin123', 1, NOW()),
       (2, 'user1', 'user123', 1, NOW()),
       (3, 'user2', 'user456', 0, NOW());

-- 插入角色
INSERT INTO sys_role (id, role_key, role_name, status, create_time)
VALUES (1, 'admin', '超级管理员', 1, NOW()),
       (2, 'editor', '编辑人员', 1, NOW()),
       (3, 'viewer', '查看人员', 1, NOW());

-- 插入权限（你已移除 method 字段，仅保留 key 和 url）
INSERT INTO sys_permission (id, permission_key, url, status, create_time)
VALUES (1, 'system:user:list', '/api/user/list', 1, NOW()),
       (2, 'system:user:add', '/api/user/add', 1, NOW()),
       (3, 'system:user:edit', '/api/user/edit', 1, NOW()),
       (4, 'system:user:delete', '/api/user/delete', 1, NOW()),
       (5, 'system:role:list', '/api/role/list', 1, NOW()),
       (6, 'system:role:edit', '/api/role/edit', 1, NOW()),
       (7, 'system:log:view', '/api/logs', 1, NOW());

-- 用户-角色关联
INSERT INTO sys_user_role (user_id, role_id)
VALUES (1, 1), -- admin 拥有管理员角色
       (2, 2), -- user1 是编辑
       (3, 3);
-- user2 是查看者

-- 角色-权限关联
-- 管理员拥有全部权限
INSERT INTO sys_role_permission (role_id, permission_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7);

-- 编辑只能增改查用户
INSERT INTO sys_role_permission (role_id, permission_id)
VALUES (2, 1),
       (2, 2),
       (2, 3);

-- 查看者只能查看用户列表和日志
INSERT INTO sys_role_permission (role_id, permission_id)
VALUES (3, 1),
       (3, 7);
