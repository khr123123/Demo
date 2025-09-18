package com.ruoyisecurity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyisecurity.constants.PasswordConstant;
import com.ruoyisecurity.domain.SysUser;
import com.ruoyisecurity.domain.SysUserRole;
import com.ruoyisecurity.domain.dto.UserPageQueryDTO;
import com.ruoyisecurity.domain.vo.PageResult;
import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.domain.vo.UserVO;
import com.ruoyisecurity.service.SysUserRoleService;
import com.ruoyisecurity.service.SysUserService;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户管理模块
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private PasswordEncoder PasswordEncoder;

    /**
     * 添加、修改用户信息
     *
     * @param sysUser
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority('system:user:edit','system:user:add')")
    public Result editUser(@RequestBody SysUser sysUser) {
        if (sysUser.getId() == null || sysUser.getId() == -1) {
            sysUser.setCreateTime(new Date());
            sysUser.setId(null);
            sysUser.setPassword(PasswordEncoder.encode(sysUser.getPassword()));
        }
        sysUser.setUpdateTime(new Date());
        sysUserService.saveOrUpdate(sysUser);
        return Result.success();
    }

    /**
     * 个人中心-修改密码
     */
    @PostMapping("/updatePwd")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        Long id = Long.valueOf(params.get("id"));
        String oldPassword = params.get("oldPassword");
        String password = params.get("password");
        SysUser sysUser = sysUserService.getById(id);
        if (!PasswordEncoder.matches(oldPassword, sysUser.getPassword())) {
            return Result.error("旧密码不正确!");
        }
        // 修改密码
        sysUser.setUpdateTime(new Date());
        sysUser.setPassword(PasswordEncoder.encode(password));
        sysUserService.updateById(sysUser);
        return Result.success();
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadImage")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result upload(@RequestPart MultipartFile file) throws ServletException {
        return Result.success(sysUserService.uploadImage(file));
    }

    /**
     * 用户列表
     *
     * @param pageQueryDTO
     * @return
     */
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result<PageResult<UserVO>> queryPageUser(@RequestBody UserPageQueryDTO pageQueryDTO) {
        return Result.success(sysUserService.queryPageUser(pageQueryDTO));
    }


    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result queryPageUser(@PathVariable Long id) {
        return Result.success(sysUserService.getById(id));
    }

    @PostMapping("/checkUserName")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result checkUserName(@RequestBody SysUser sysUser) {
        SysUser one = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, sysUser.getUsername()));
        if (one != null) {
            return Result.error("用户已存在");
        }
        return Result.success("用户不存在");
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result save(@RequestBody SysUser sysUser) {
        log.info("保存用户信息:{}", sysUser);
        if (sysUser.getId() == -1) {
            sysUser.setId(null);
        }
        return Result.success(sysUserService.saveOrUpdate(sysUser));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('system:user:query')")
    public Result delete(String id) {
        if ("1".equals(id)) {
            return Result.error("超级管理员不能被删除");
        }
        boolean b = sysUserService.removeById(id);
        boolean remove = sysUserRoleService.remove(new LambdaUpdateWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
        return Result.success(b && remove);
    }

    /**
     * 修改状态
     *
     * @param id
     * @return
     */
    @GetMapping("/updateStatus/{id}/status/{status}")
    @PreAuthorize("hasAuthority('system:user:edit')")
    public Result<String> updateStatus(@PathVariable Long id, @PathVariable String status) {
        SysUser sysUser = SysUser.builder().id(id).status(status).updateTime(new Date()).build();
        sysUserService.updateById(sysUser);
        return Result.success();
    }


    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @GetMapping("/resetPassword/{id}")
    @PreAuthorize("hasAuthority('system:user:resetPwd')")
    public Result<String> resetPassword(@PathVariable Long id) {
        SysUser sysUser = SysUser.builder().id(id).password(bCryptPasswordEncoder.encode(PasswordConstant.DEFAULT_PASSWORD)).updateTime(new Date()).build();
        sysUserService.updateById(sysUser);
        return Result.success();
    }

    /**
     * 用户角色授权
     *
     * @param id
     * @param roles
     * @return
     */
    @PostMapping("/grantRole/{id}")
    @PreAuthorize("hasAuthority('system:user:role')")
    public Result<String> grantRole(@PathVariable Long id, @RequestParam List<Long> roles) {
        sysUserService.grantRole(id, roles);
        return Result.success();
    }
}