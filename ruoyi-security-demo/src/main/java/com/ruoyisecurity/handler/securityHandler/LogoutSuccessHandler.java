package com.ruoyisecurity.handler.securityHandler;

import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.utils.JsonUtils;
import com.ruoyisecurity.utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 退出成功处理器
 */
@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ServletUtils.renderString(response, JsonUtils.toString(Result.success("退出成功")));
    }
}