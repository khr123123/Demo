package com.ruoyisecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyisecurity.domain.SysRole;

import java.util.List;

/**
* @author KKHR
* @description 针对表【sys_role】的数据库操作Service
* @createDate 2024-10-31 11:57:50
*/
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getUserRolesList(Long id);

    List<Long> roleMenusById(Long id);

    void grantMenu(Long id, List<Long> menus);
}
