package org.khr.demo.controller;

import com.mybatisflex.core.paginate.Page;
import org.khr.demo.dao.entity.SysRolePermission;
import org.khr.demo.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  控制层。
 *
 * @author KK
 * @since 2025-07-31 14:13:27
 */
@RestController
@RequestMapping("/sysRolePermission")
public class SysRolePermissionController {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 保存。
     *
     * @param sysRolePermission 
     * @return {@code true} 保存成功，{@code false} 保存失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody SysRolePermission sysRolePermission) {
        return sysRolePermissionService.save(sysRolePermission);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return sysRolePermissionService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param sysRolePermission 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody SysRolePermission sysRolePermission) {
        return sysRolePermissionService.updateById(sysRolePermission);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<SysRolePermission> list() {
        return sysRolePermissionService.list();
    }

    /**
     * 根据主键获取。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public SysRolePermission getInfo(@PathVariable Long id) {
        return sysRolePermissionService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<SysRolePermission> page(Page<SysRolePermission> page) {
        return sysRolePermissionService.page(page);
    }

}
