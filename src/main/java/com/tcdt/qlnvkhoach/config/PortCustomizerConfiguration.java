package com.tcdt.qlnvkhoach.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

@Component
@Slf4j
public class PortCustomizerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Value("${port.range:30000,30999}")
    private Integer[] portRange;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        int port = SocketUtils.findAvailableTcpPort(portRange[0], portRange[1]);
        factory.setPort(1702);
        System.setProperty("server.port", String.valueOf(port));
        log.info("Random Server Port is set to {} - {}.", port);
    }
}
