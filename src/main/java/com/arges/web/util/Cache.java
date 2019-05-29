package com.arges.web.util;

import org.aspectj.lang.annotation.Aspect;

/**
 * 使用Ehcache或redis等缓存
 *  
 * @author zhangjie
 * 
 */
@Aspect
public class Cache {
    public static String getCachedValue() {
        return "1234";
    }
}
