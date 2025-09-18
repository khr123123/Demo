package com.ruoyisecurity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security")
@Data
public class SecurityProperties {

    /**
     * 请求白名单
     */
    private String[] whiteList;
}