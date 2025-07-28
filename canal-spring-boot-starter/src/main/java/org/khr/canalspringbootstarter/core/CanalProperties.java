package org.khr.canalspringbootstarter.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "canal")
public record CanalProperties(
    boolean enabled,
    String host,
    Integer port,
    String destination,
    String username,
    String password, String dbName) {

}
