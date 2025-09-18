package com.ruoyisecurity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JwtProperties {
    /**
     * jwt 过期时间，单位分钟
     */
    private long expireTime;
    /**
     * 密钥
     */
    private String secret;


}
