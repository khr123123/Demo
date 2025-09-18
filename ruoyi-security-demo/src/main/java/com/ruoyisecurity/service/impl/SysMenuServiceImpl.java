package com.ruoyisecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyisecurity.domain.SysMenu;
import com.ruoyisecurity.domain.SysRoleMenu;
import com.ruoyisecurity.mapper.SysMenuMapper;
import com.ruoyisecurity.service.SysMenuService;
import com.ruoyisecurity.service.SysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author KKHR
 * @description 针对表【sys_menu】的数据库操作Service实现
 * @createDate 2024-10-31 11:57:50
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysRoleMenuService SysRoleMenuService;

    /**
     * 生成菜单树
     * @param sysMenuList 菜单列表
     * @return 菜单树结构
     */
    @Override
    public List<SysMenu> buildTreeMenu(List<SysMenu> sysMenuList) {
        // 对原始菜单实现排序
        sysMenuList.sort(Comparator.comparing(SysMenu::getOrderNum));
        // 过滤掉类型为 "F" 的菜单
        // sysMenuList = sysMenuList.stream()
        //         .filter(sysMenu -> !Objects.equals(sysMenu.getMenuType(), "F"))
        //         .toList();
        // 创建一个 Map 来存储所有菜单
        Map<Long, SysMenu> menuMap = sysMenuList.stream()
                .collect(Collectors.toMap(SysMenu::getId, Function.identity()));
        // 挂载子菜单
        List<SysMenu> sysMenus = new ArrayList<>();
        for (SysMenu menu : sysMenuList) {
            if (menu.getParentId() == 0) {
                // 添加父节点
                sysMenus.add(menu);
            } else {
                // 将子节点挂到父节点上
                SysMenu parentMenu = menuMap.get(menu.getParentId());
                if (parentMenu != null) {
                    parentMenu.getChildren().add(menu);
                }
            }
        }
        return sysMenus;
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        // 1. 递归查找菜单
        List<Long> menuIds = recursiveMenus(id);
        // 2. 删除权限与角色关系
        SysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>()
                .in(SysRoleMenu::getMenuId, menuIds));
        // 3. 删除权限
        removeBatchByIds(menuIds);
        // 4. 日志记录
        log.info("Deleted menus with IDs: {}", menuIds);
    }

    /**
     * 递归查找菜单
     * @param parentId 父菜单ID
     * @return 包含所有子菜单ID的列表
     */
    public List<Long> recursiveMenus(Long parentId) {
        List<Long> menuIds = new ArrayList<>();
        List<SysMenu> sysMenus = list(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, parentId));
        menuIds.add(parentId); // 添加当前菜单ID
        for (SysMenu menu : sysMenus) {
            menuIds.addAll(recursiveMenus(menu.getId())); // 递归添加子菜单ID
        }
        return menuIds;
    }


}
