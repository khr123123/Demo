package com.ruoyisecurity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyisecurity.constants.CacheConstant;
import com.ruoyisecurity.domain.SysRole;
import com.ruoyisecurity.domain.SysRoleMenu;
import com.ruoyisecurity.domain.SysUserRole;
import com.ruoyisecurity.domain.dto.RolePageQueryDTO;
import com.ruoyisecurity.domain.vo.PageResult;
import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.service.SysRoleMenuService;
import com.ruoyisecurity.service.SysRoleService;
import com.ruoyisecurity.service.SysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色管理模块
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 返回角色列表ALLALLALL
     *
     * @return
     */
    @GetMapping("/listAll")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<List<SysRole>> listAll() {
        return Result.success(sysRoleService.list());
    }

    /**
     * 角色列表
     *
     * @param pageQueryDTO
     * @return
     */
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<PageResult<SysRole>> queryPageRole(@RequestBody RolePageQueryDTO pageQueryDTO) {
        Page<SysRole> page = sysRoleService.page(new Page<>(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize())
                , new LambdaQueryWrapper<SysRole>().eq(StringUtils.isNoneBlank(pageQueryDTO.getName()),
                        SysRole::getName, pageQueryDTO.getName()));
        return Result.success(new PageResult<>(page.getTotal(), page.getRecords()));
    }

    /**
     * 添加/修改角色信息
     *
     * @param sysRole
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('system:role:edit','system:role:add')")
    @CacheEvict(value = CacheConstant.AUTHORITY_KEY, allEntries = true)
    public Result save(@RequestBody SysRole sysRole) {
        if (sysRole.getId() == null || sysRole.getId() == -1) {
            sysRole.setCreateTime(new Date());
            sysRole.setUpdateTime(new Date());
            sysRole.setId(null);
        }
        sysRole.setUpdateTime(new Date());
        return Result.success(sysRoleService.saveOrUpdate(sysRole));
    }

    /**
     * 删除角色
     *
     * @param
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('system:role:delete')")
    @CacheEvict(value = CacheConstant.AUTHORITY_KEY, allEntries = true)
    public Result deleteRole(@PathVariable Long id) {
        sysRoleService.removeById(id);
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
        return Result.success();
    }


    /**
     * 查询角色对应权限ID
     *
     * @return
     */
    @GetMapping("/menu/{id}")
    @PreAuthorize("hasAuthority('system:role:query')")
    public Result<List<Long>> roleMenus(@PathVariable Long id) {
        return Result.success(sysRoleService.roleMenusById(id));
    }

    /**
     * 角色授权
     *
     * @param id
     * @param
     * @return
     */
    @PostMapping("/grantMenu/{id}")
    @PreAuthorize("hasAuthority('system:role:menu')")
    @CacheEvict(value = CacheConstant.AUTHORITY_KEY, allEntries = true)
    public Result<String> grantMenu(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        String menuIds = (String) params.get("menus");
        List<Long> menuIdList = Arrays.stream(menuIds.split(",")).map(Long::parseLong).toList();
        sysRoleService.grantMenu(id, menuIdList);
        return Result.success();
    }
}