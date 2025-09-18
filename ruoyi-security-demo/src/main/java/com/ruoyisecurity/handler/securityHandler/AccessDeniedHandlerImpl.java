package com.ruoyisecurity.handler.securityHandler;

import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.utils.JsonUtils;
import com.ruoyisecurity.utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 对已认证用户无权限的处理
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    // 返回403无权限状态码
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ServletUtils.renderString(response,
                JsonUtils.toString(Result.error(HttpStatus.FORBIDDEN.value(), e.getMessage())));
    }
}