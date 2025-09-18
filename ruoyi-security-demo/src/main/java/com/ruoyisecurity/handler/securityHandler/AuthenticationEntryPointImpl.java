package com.ruoyisecurity.handler.securityHandler;

import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.utils.JsonUtils;
import com.ruoyisecurity.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 未登录认证 返回结果
 * 替换同步请求 login.html页面
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        // 未登录返回 401 状态码
        ServletUtils.renderString(response,
                JsonUtils.toString(Result.error(HttpStatus.UNAUTHORIZED.value(), authException.getMessage())));
    }
}