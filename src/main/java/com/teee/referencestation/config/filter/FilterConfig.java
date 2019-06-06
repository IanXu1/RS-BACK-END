package com.teee.referencestation.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglei
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean defendTamperFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DefendTamperFilter());
        List<String> urlList = new ArrayList<>();
        urlList.add("/*");
        registration.setUrlPatterns(urlList);
        registration.setName("DefendTamperFilter");
        registration.setOrder(1);
        return registration;
    }
}
