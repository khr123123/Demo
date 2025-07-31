package org.khr.demo.controller;

import com.mybatisflex.core.paginate.Page;
import org.khr.demo.dao.entity.SysUserRole;
import org.khr.demo.service.SysUserRoleService;
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
@RequestMapping("/sysUserRole")
public class SysUserRoleController {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 保存。
     *
     * @param sysUserRole 
     * @return {@code true} 保存成功，{@code false} 保存失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody SysUserRole sysUserRole) {
        return sysUserRoleService.save(sysUserRole);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return sysUserRoleService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param sysUserRole 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody SysUserRole sysUserRole) {
        return sysUserRoleService.updateById(sysUserRole);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<SysUserRole> list() {
        return sysUserRoleService.list();
    }

    /**
     * 根据主键获取。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public SysUserRole getInfo(@PathVariable Long id) {
        return sysUserRoleService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<SysUserRole> page(Page<SysUserRole> page) {
        return sysUserRoleService.page(page);
    }

}
