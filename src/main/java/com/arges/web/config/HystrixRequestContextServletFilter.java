package com.arges.web.config;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * 对于一个request context内的多个相同command，使用request cache，提升性能
 * 利用请求上下文，缓存key,避免重复查询。可以避免批量删除、查询等操作的请求。
 *  
 * @author zhangjie
 * 
 */
public class HystrixRequestContextServletFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            chain.doFilter(request, response);
        } finally {
            context.shutdown();
        }
    }
}
