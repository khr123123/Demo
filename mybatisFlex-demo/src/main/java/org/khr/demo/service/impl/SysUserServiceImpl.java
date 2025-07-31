package org.khr.demo.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.khr.demo.dao.entity.SysUser;
import org.khr.demo.dao.mapper.SysUserMapper;
import org.khr.demo.model.UserPermissionVO;
import org.khr.demo.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.khr.demo.dao.table.SysPermissionTableDef.SYS_PERMISSION;
import static org.khr.demo.dao.table.SysRolePermissionTableDef.SYS_ROLE_PERMISSION;
import static org.khr.demo.dao.table.SysRoleTableDef.SYS_ROLE;
import static org.khr.demo.dao.table.SysUserRoleTableDef.SYS_USER_ROLE;
import static org.khr.demo.dao.table.SysUserTableDef.SYS_USER;

/**
 * 服务层实现。
 *
 * @author KK
 * @since 2025-07-31 14:13:27
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;

    @Override
    public List<UserPermissionVO> showPermission(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(SYS_USER.ID.as(UserPermissionVO::getUserId),
                SYS_USER.USERNAME.as(UserPermissionVO::getUsername),
                SYS_ROLE.ROLE_KEY.as(UserPermissionVO::getRoleKey),
                SYS_PERMISSION.PERMISSION_KEY.as(UserPermissionVO::getPermissionKey),
                SYS_PERMISSION.URL.as(UserPermissionVO::getPermissionUrl))
            .from(SYS_USER)
            .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.USER_ID.eq(SYS_USER.ID))
            .leftJoin(SYS_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ID))
            .leftJoin(SYS_ROLE_PERMISSION).on(SYS_ROLE_PERMISSION.ROLE_ID.eq(SYS_ROLE.ID))
            .leftJoin(SYS_PERMISSION).on(SYS_ROLE_PERMISSION.PERMISSION_ID.eq(SYS_PERMISSION.ID))
            .where(SYS_USER.ID.eq(userId));
        return userMapper.selectListByQueryAs(queryWrapper, UserPermissionVO.class);
    }
}
