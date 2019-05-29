package com.arges.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 *  
 * @author zhangjie
 *
 */
@Configuration
public class Config {


    @Bean
    public FilterRegistrationBean indexFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new HystrixRequestContextServletFilter());
        registration.addUrlPatterns("/*");//todo 可以用 /或/* ，使用 /** 没有用
        return registration;
    }
}
