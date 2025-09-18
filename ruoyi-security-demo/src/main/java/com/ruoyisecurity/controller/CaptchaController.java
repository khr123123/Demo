package com.ruoyisecurity.controller;

import com.ruoyisecurity.constants.CacheConstant;
import com.ruoyisecurity.domain.vo.Result;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码控制器
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class CaptchaController {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 采用Base64转码方式实现验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public Result<Map<String, Object>> captcha() {
        // 1.生成随机唯一key
        UUID uuid = UUID.randomUUID();
        String key = CacheConstant.CAPTCHA_KEY + uuid;
        // 2.生成GIF验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha();
        String code = captcha.text(); // 获取验证码值
        String base64 = captcha.toBase64(); // 生成Base64编码
        // 3.使用Redis存储key-code
        stringRedisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
        return Result.success(Map.of("img", base64, "uuid", uuid));
    }

}