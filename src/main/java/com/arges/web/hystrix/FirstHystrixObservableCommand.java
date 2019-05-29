package com.arges.web.hystrix;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.arges.web.util.MyHttpCilentUtils;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * 利用信号量，不会创建hystrix线程池，无法进行线程数量（限流）和超时限制.
 * 因此当请求量过大或某个响应服务奔溃导致的请求超时，都是导致请求阻塞。
 * 但是可以通过熔断、降级机制快速失败，避免不必要的请求。
 *
 * 信号量可以实现请求缓存（线程池不可以），通过批量请求，将发送的多个http request合并在一个HystrixRequestContext request上下文中。
 *
 * @author zhangjie
 */
public class FirstHystrixObservableCommand extends HystrixObservableCommand<String> {

    private final List<Map<String,String>> paramsList;

    public FirstHystrixObservableCommand(List<Map<String, String>> paramsList) {
//        super(HystrixCommandGroupKey.Factory.asKey("getInfoGroup"));
        super(HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("getInfoGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationSemaphoreMaxConcurrentRequests(1).withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)));
        this.paramsList = paramsList;
        System.out.println("调用observable command构造函数");
    }

    @Override
    protected Observable construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                observer.onStart();
                System.out.println("调用call 方法");
                try {
                    for(Map<String,String> paramMap : paramsList){
                        String url = paramMap.remove("url");
                        if(!StringUtils.hasText(url)){
                            throw new IllegalArgumentException("url 不可为空");
                        }
                        observer.onNext(MyHttpCilentUtils.postRequest(url,paramMap));
                    }
                    observer.onCompleted();
                }catch (Exception e){
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public String getCacheKey(){
        return String.valueOf(paramsList.hashCode());
    }
}
