package com.ruoyisecurity.config;

import com.ruoyisecurity.filters.CaptchaAuthenticationFilter;
import com.ruoyisecurity.filters.JwtAuthenticationTokenFilter;
import com.ruoyisecurity.handler.securityHandler.*;
import com.ruoyisecurity.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityProperties securityProperties; // 注入安全属性配置(白名单)

    private final LoginFailureHandler loginFailureHandler;    // 登录失败处理器

    private final LoginSuccessHandler loginSuccessHandler;    // 登录成功处理器

    private final AuthenticationEntryPointImpl authenticationEntryPoint;    // 未认证 且未授权 处理器

    private final AccessDeniedHandlerImpl accessDeniedHandler;     // 已认证 但未授权 处理器

    private final LogoutSuccessHandler logoutSuccessHandler;    // 退出成功处理器

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;    // JWT认证过滤器
    private final CaptchaAuthenticationFilter captchaAuthenticationFilter;    // 验证码验证过滤器


    /**
     * 密码编码器
     * @return BCryptPasswordEncoder实例
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 安全过滤链配置
     * @param http HttpSecurity实例
     * @return SecurityFilterChain实例
     * @throws Exception 可能抛出的异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 关闭跨站请求攻击
                .csrf(AbstractHttpConfigurer::disable)
                // 关闭跨域
                .cors(Customizer.withDefaults())
                // 前后端分离模式,关闭Session管理器
                .sessionManagement((configurer) -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 请求授权配置
                .authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests.requestMatchers(securityProperties.getWhiteList())// 放行白名单
                            .permitAll().anyRequest().authenticated(); // 拦截所有其他请求
                })
                // 表单登录配置
                .formLogin(configurer -> {
                    configurer
                            // 登录请求地址
                            .loginProcessingUrl("/login")
                            // 登录成功处理器
                            .successHandler(loginSuccessHandler)
                            // 登录失败处理器
                            .failureHandler(loginFailureHandler);
                })
                // 退出登录配置
                .logout((configurer) -> {
                    configurer.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler); // 退出成功处理器
                })
                // 异常处理配置
                .exceptionHandling(configurer -> configurer
                        // 未认证 且未授权 处理器
                        .authenticationEntryPoint(authenticationEntryPoint)
                        // 已认证 但未授权 处理器
                        .accessDeniedHandler(accessDeniedHandler))
                // 添加验证码验证过滤器
                .addFilterBefore(captchaAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 添加JWT认证过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
