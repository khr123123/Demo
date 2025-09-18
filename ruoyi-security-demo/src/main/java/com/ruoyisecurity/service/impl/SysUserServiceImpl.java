package com.ruoyisecurity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyisecurity.constants.CacheConstant;
import com.ruoyisecurity.domain.SysMenu;
import com.ruoyisecurity.domain.SysRole;
import com.ruoyisecurity.domain.SysUser;
import com.ruoyisecurity.domain.SysUserRole;
import com.ruoyisecurity.domain.dto.UserPageQueryDTO;
import com.ruoyisecurity.domain.vo.PageResult;
import com.ruoyisecurity.domain.vo.UserVO;
import com.ruoyisecurity.mapper.SysUserMapper;
import com.ruoyisecurity.service.SysMenuService;
import com.ruoyisecurity.service.SysRoleService;
import com.ruoyisecurity.service.SysUserRoleService;
import com.ruoyisecurity.service.SysUserService;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KKHR
 * @description 针对表【sys_user】的数据库操作Service实现
 * @createDate 2024-10-31 11:57:49
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUser selectUserByUserName(String username) {
        return getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

    @Override
    @Cacheable(value = CacheConstant.AUTHORITY_KEY, key = "#userId")
    public String getUserAuthorityString(Long userId) {
        List<SysRole> sysRoles = sysRoleService.getUserRolesList(userId);
        List<String> roleList = sysRoles.stream().map(SysRole::getCode).map("ROLE_"::concat).toList();
        List<SysMenu> menuList = getUserMenuList(userId);
        List<String> PermsList = menuList.stream().map(SysMenu::getPerms).filter(StringUtils::isNoneBlank).toList();
        return CollUtil.join(roleList, ",") + "," + CollUtil.join(PermsList, ",");
    }

    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        List<SysMenu> sysMenuList = new ArrayList<>();
        // 1.加载角色列表
        List<SysRole> sysRoles = sysRoleService.getUserRolesList(userId);
        // 2.加载权限列表
        // 获取角色列表
        List<String> roleIds = sysRoles.stream().map(SysRole::getId).map(String::valueOf).toList();
        if (!roleIds.isEmpty()) {
            // 获取角色字符串
            // select DISTINCT * from sys_menu where id in (select menu_id from sys_role_menu where role_id in (1,2))
            String ids = String.join(",", roleIds);
            sysMenuList = sysMenuService.list(new QueryWrapper<SysMenu>()
                    .select("DISTINCT *").lambda()
                    .inSql(SysMenu::getId, "select menu_id from sys_role_menu where role_id in (" + ids + ")"));
        }
        return sysMenuList;
    }

    @Value("${uploadImagePath}")
    private String uploadImagePath;

    @Override
    public String uploadImage(MultipartFile file) throws ServletException {
        try {
            // 3.实现文件上传
            String ext = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            String fileName = System.currentTimeMillis() + (StringUtils.isNoneBlank(ext) ? ("." + ext) : "");
            // 4.实现上传,返回结果
            file.transferTo(new File(uploadImagePath, fileName));
            return fileName;
        } catch (Exception e) {
            log.error("【文件上传】出现异常:{}", e.getMessage());
            throw new ServletException("文件上传失败");
        }
    }


    /**
     * 用户列表
     *
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult<UserVO> queryPageUser(UserPageQueryDTO pageQueryDTO) {
        Page<SysUser> userPage = page(new Page<>(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize()),
                new LambdaQueryWrapper<SysUser>().like(StringUtils.isNoneBlank(pageQueryDTO.getName()), SysUser::getUsername, pageQueryDTO.getName()));
        List<UserVO> userVOS = BeanUtil.copyToList(userPage.getRecords(), UserVO.class);
        userVOS.forEach(sysUser -> {
            List<SysRole> userRolesList = sysRoleService.getUserRolesList(sysUser.getId());
            sysUser.setSysRoleList(userRolesList);
        });
        return new PageResult<>(userPage.getTotal(), userVOS);
    }

    @Override
    public void grantRole(Long id, List<Long> roles) {
        // 删除所有角色
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, id));
        // 维护用户与角色关联数据
        List<SysUserRole> sysUserRoles = roles.stream().map(r -> SysUserRole.builder()
                .userId(id)
                .roleId(r).build()
        ).toList();
        // 批量添加关联角色
        sysUserRoleService.saveBatch(sysUserRoles);
    }
}




