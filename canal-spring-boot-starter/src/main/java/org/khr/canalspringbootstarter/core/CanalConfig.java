package org.khr.canalspringbootstarter.core;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
@EnableConfigurationProperties(CanalProperties.class)
@RequiredArgsConstructor
public class CanalConfig {

    private final CanalProperties canalProperties;

    @Bean
    public CanalConnector canalConnector() {
        return CanalConnectors.newSingleConnector(
            new InetSocketAddress(canalProperties.host(), canalProperties.port()),
            canalProperties.destination(),
            canalProperties.username(),
            canalProperties.password());
    }
}
