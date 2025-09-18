package com.ruoyisecurity.handler.securityHandler;

import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.utils.JsonUtils;
import com.ruoyisecurity.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 登录失败处理
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        // 返回失败异常信息
        ServletUtils.renderString(response, JsonUtils.toString(Result.error(exception.getMessage())));
    }
}