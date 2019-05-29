package com.arges.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhangjie
 */
@SpringBootApplication
@ImportResource(value = {"classpath:consumer.xml"})
@EnableAspectJAutoProxy
public class HystrixProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixProviderApplication.class, args);
    }
}
