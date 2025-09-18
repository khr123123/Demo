package com.ruoyisecurity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyisecurity.constants.CacheConstant;
import com.ruoyisecurity.domain.SysMenu;
import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 权限管理模块
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 返回权限树列表
     *
     * @return
     */
    @GetMapping("/treeList")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public Result<List<SysMenu>> treeList() {
        return Result.success(sysMenuService.buildTreeMenu(sysMenuService.list(
                new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getOrderNum))));
    }

    /**
     * 根据主键查询角色
     *
     * @param id
     * @return
     */
    @GetMapping("/getMenuById/{id}")
    @PreAuthorize("hasAuthority('system:menu:query')")
    public Result<SysMenu> getMenuById(@PathVariable Long id) {
        return Result.success(sysMenuService.getById(id));
    }

    /**
     * 保存权限信息
     *
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('system:menu:edit','system:menu:add')")
    @CacheEvict(value = CacheConstant.AUTHORITY_KEY, allEntries = true)
    public Result<String> save(@RequestBody @Validated SysMenu sysMenu) {
        if (sysMenu.getId() == null || sysMenu.getId() == -1) {
            sysMenu.setCreateTime(new Date());
            sysMenu.setUpdateTime(new Date());
            sysMenu.setId(null);
        }
        sysMenu.setUpdateTime(new Date());
        sysMenuService.saveOrUpdate(sysMenu);
        return Result.success();
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('system:menu:delete')")
    @CacheEvict(value = CacheConstant.AUTHORITY_KEY, allEntries = true)
    public Result<String> deleteMenu(@RequestParam Long id) {
        sysMenuService.deleteMenu(id);
        return Result.success();
    }
}