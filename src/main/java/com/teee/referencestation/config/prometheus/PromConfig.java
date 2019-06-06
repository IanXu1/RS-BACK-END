package com.teee.referencestation.config.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanglei
 */
@Configuration
public class PromConfig {

    @Autowired
    PrometheusMeterRegistry registry;

    @Bean
    public Counter baseStationCounter() {
        Counter counter = Counter.builder("base station query list count")
                .tags("status", "success")
                .description("base station query list count")
                .register(registry);
        return counter;
    }
}
