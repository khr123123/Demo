package com.ruoyisecurity.filters;

import com.ruoyisecurity.constants.CacheConstant;
import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.utils.JsonUtils;
import com.ruoyisecurity.utils.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 验证码过滤器
 * OncePerRequestFilter 每次请求之前拦截
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CaptchaAuthenticationFilter extends OncePerRequestFilter implements Ordered {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1.获取请求路径
        String requestURI = request.getRequestURI();
        // 2.如果是登录并且是POST请求
        if ("/login".equals(requestURI) && request.getMethod().equalsIgnoreCase("POST")) {
            try {
                validateCaptcha(request);
            } catch (ServletException e) {
                log.info("验证码解析错误:{}", e.getMessage());
                ServletUtils.renderString(response, JsonUtils.toString(Result.error(e.getMessage())));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 校验验证码
     *
     * @param request
     */
    private void validateCaptcha(HttpServletRequest request) throws ServletException {
        String code = request.getParameter("code");
        String uuid = request.getParameter("uuid");
        String key = CacheConstant.CAPTCHA_KEY + uuid;
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(key))) {
            throw new ServletException("验证码已过期");
        }
        String codeValue = stringRedisTemplate.opsForValue().get(key);
        if (!code.equalsIgnoreCase(codeValue)) {
            throw new ServletException("验证码错误");
        }
        // 验证成功 删除缓存数据
        stringRedisTemplate.delete(key);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}