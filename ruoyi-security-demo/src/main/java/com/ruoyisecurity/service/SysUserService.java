package com.ruoyisecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyisecurity.domain.SysMenu;
import com.ruoyisecurity.domain.SysUser;
import com.ruoyisecurity.domain.dto.UserPageQueryDTO;
import com.ruoyisecurity.domain.vo.PageResult;
import com.ruoyisecurity.domain.vo.UserVO;
import jakarta.servlet.ServletException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author KKHR
 * @description 针对表【sys_user】的数据库操作Service
 * @createDate 2024-10-31 11:57:50
 */
public interface SysUserService extends IService<SysUser> {

    SysUser selectUserByUserName(String username);

    String getUserAuthorityString(Long userId);

    List<SysMenu> getUserMenuList(Long userId);

    Object uploadImage(MultipartFile file) throws ServletException;

    PageResult<UserVO> queryPageUser(UserPageQueryDTO pageQueryDTO);

    void grantRole(Long id, List<Long> roles);

}
