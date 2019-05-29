package com.arges.web.hystrix;

import com.arges.web.util.Cache;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * 
 *  
 * @author zhangjie
 * 
 */
public class TestFallbackCommand extends HystrixCommand<String> {

    public TestFallbackCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("getInfoGroup"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(1)//设置请求线程数量最大为1个
                        .withMaxQueueSize(0)//设置请求的排队队长为0；
                ));
    }

    @Override
    protected String run() throws Exception {
        throw new Exception("test fallback");
    }

    @Override
    protected String getFallback() {
        return Cache.getCachedValue();
    }
}
