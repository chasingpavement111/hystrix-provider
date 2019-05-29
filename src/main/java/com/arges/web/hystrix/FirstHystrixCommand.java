package com.arges.web.hystrix;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.arges.web.util.MyHttpCilentUtils;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @author zhangjie
 */
public class FirstHystrixCommand extends HystrixCommand<String> {

    private final String url;

    private final Map<String, String> paramsMap;

    public FirstHystrixCommand(String url) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("getInfoGroup"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("theadPoolKey"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withMaxQueueSize(2))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.url = url;
        paramsMap = new HashMap<>();
        System.out.println("调用command构造函数");
    }

    public FirstHystrixCommand(Map<String, String> paramsMap) {
        super(HystrixCommandGroupKey.Factory.asKey("getInfoGroup"));
//        HystrixCommandProperties.Setter obj = HystrixCommandProperties.Setter()
//                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);


        url = paramsMap.remove("url");
        if (!StringUtils.hasText(url)) {
            throw new IllegalArgumentException("url 不可为空");
        }
        this.paramsMap = paramsMap;
        System.out.println("调用command构造函数");
    }

    @Override
    protected String run() throws Exception {
        System.out.println("调用run 方法");
        return MyHttpCilentUtils.getRequest(url, paramsMap);
    }

    @Override
    protected String getCacheKey() {
        if (paramsMap == null || paramsMap.size() == 0) {
            return url;
        }
        StringBuffer paramStr = new StringBuffer();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            paramStr.append(",").append(entry.getKey()).append("=").append(entry.getValue());
        }
        String key =  url + ":{" + paramStr.substring(1) + "}";
//        System.out.println(key);
        return key;
    }
}
