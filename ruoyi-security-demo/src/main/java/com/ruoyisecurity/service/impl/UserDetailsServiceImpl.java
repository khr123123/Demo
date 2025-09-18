package com.ruoyisecurity.service.impl;

import com.ruoyisecurity.domain.SysUser;
import com.ruoyisecurity.enums.UserStatus;
import com.ruoyisecurity.exception.ServiceException;
import com.ruoyisecurity.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 自定义登录逻辑
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = sysUserService.selectUserByUserName(username);
        if (user == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("账号或密码错误");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("账号已被停用");
        }
        return new User(user.getUsername(), user.getPassword(), getUserAuthority(user.getId()));
    }

    /**
     * 处理用户权限
     */
    public List<GrantedAuthority> getUserAuthority(Long userId) {
        // 加载角色与权限
        String authorityString = sysUserService.getUserAuthorityString(userId);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
    }

}