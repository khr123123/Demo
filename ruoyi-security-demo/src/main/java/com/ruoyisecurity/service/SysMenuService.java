package com.ruoyisecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyisecurity.domain.SysMenu;

import java.util.List;

/**
 * @author KKHR
 * @description 针对表【sys_menu】的数据库操作Service
 * @createDate 2024-10-31 11:57:50
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> buildTreeMenu(List<SysMenu> sysMenuList);

    void deleteMenu(Long id);
}
