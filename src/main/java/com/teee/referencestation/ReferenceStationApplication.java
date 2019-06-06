package com.teee.referencestation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 项目入口类
 *
 * @author LDB
 * @date 2019/01/03 17:35
 */
@EnableTransactionManagement
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableSwagger2
public class ReferenceStationApplication extends SpringBootServletInitializer {

    /**
     * jsp支持
     *
     * @param builder SpringApplicationBuilderSoftInstallation
     * @return SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ReferenceStationApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReferenceStationApplication.class, args);
    }
}

