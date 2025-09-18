package com.ruoyisecurity.filters;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ruoyisecurity.constants.JwtClaimsConstant;
import com.ruoyisecurity.domain.SysUser;
import com.ruoyisecurity.exception.ServiceException;
import com.ruoyisecurity.properties.SecurityProperties;
import com.ruoyisecurity.service.SysUserService;
import com.ruoyisecurity.service.impl.UserDetailsServiceImpl;
import com.ruoyisecurity.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * JWT过滤器
 * OncePerRequestFilter 每次请求之前拦截
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter implements Ordered {

    private final SecurityProperties securityProperties;

    private final SysUserService sysUserService;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            String token = request.getHeader("authorization");
            String requestURI = request.getRequestURI();
            // 如果是白名单直接放行
            if (StringUtils.isBlank(token) || Arrays.stream(securityProperties.getWhiteList()).toList().contains(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }
            // 解析token
            Claims claims = jwtUtil.parseJWT(token);
            String username = (String) claims.get(JwtClaimsConstant.USER_USERNAME);
            SysUser sysUser = sysUserService.selectUserByUserName(username);
            // 重新读取用户所有权限
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    sysUser.getUsername(),
                    null,
                    userDetailsServiceImpl.getUserAuthority(sysUser.getId()));
            // 将权限保存到SecurityContextHolder上下文(更新权限)
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            // 此处不需要触发异常,移交给认证器AuthenticationEntryPointImpl处理
            throw new ServiceException("登陆状态失效，请重新登录！");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}