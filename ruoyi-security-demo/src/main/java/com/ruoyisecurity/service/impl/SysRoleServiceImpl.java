package com.ruoyisecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyisecurity.domain.SysRole;
import com.ruoyisecurity.domain.SysRoleMenu;
import com.ruoyisecurity.mapper.SysRoleMapper;
import com.ruoyisecurity.service.SysRoleMenuService;
import com.ruoyisecurity.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author KKHR
 * @description 针对表【sys_role】的数据库操作Service实现
 * @createDate 2024-10-31 11:57:50
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
        implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysRole> getUserRolesList(Long userId) {
        return list(new LambdaQueryWrapper<SysRole>()
                .inSql(SysRole::getId, "select role_id from sys_user_role where user_id = " + userId));
    }

    @Override
    public List<Long> roleMenusById(Long id) {
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, id));
        return sysRoleMenus.stream().map(SysRoleMenu::getMenuId).toList();
    }

    @Override
    @Transactional
    public void grantMenu(Long id, List<Long> menus) {
        // 1.删除所有权限
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, id));
        // 2.维护角色权限关系
        List<SysRoleMenu> roleMenuList = menus.stream().map(mid -> SysRoleMenu.builder()
                .roleId(id)
                .menuId(mid).build()).toList();
        sysRoleMenuService.saveBatch(roleMenuList);
    }
}




