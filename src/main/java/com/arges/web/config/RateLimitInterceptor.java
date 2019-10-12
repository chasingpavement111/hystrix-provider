package com.arges.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 限制服务每秒只能处理一个请求
 *
 * @author zhangjie
 */
public class RateLimitInterceptor extends HandlerInterceptorAdapter {

    private static final RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        double earliestAvailableTimestamp = rateLimiter.acquire(1);
        if (!rateLimiter.tryAcquire()) {
            //若此时无法直接被执行，被限流直接返回
            System.out.println("被限流");
            return false;
        }
        System.out.println("通过");
        return true;
    }
}
