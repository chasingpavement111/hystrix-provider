package com.arges.web.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arges.web.hystrix.FirstHystrixCommand;
import com.arges.web.hystrix.FirstHystrixObservableCommand;
import com.fasterxml.jackson.databind.ObjectMapper;

import rx.Observable;
import rx.Observer;
import rx.observables.BlockingObservable;

/**
 * @author zhangjie
 */
@RestController
@RequestMapping
public class RequestController {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @GetMapping
    public String getInfo(@RequestParam Map<String, String> baseInfo) throws IOException {
        // = "http://127.0.0.1:8011/api/getCode";
        FirstHystrixCommand command = new FirstHystrixCommand(baseInfo);
//        TestFallbackCommand command = new TestFallbackCommand();
        String result = command.execute();
        System.out.println(command.isResponseFromCache());
//        return OBJECT_MAPPER.readValue(result, ResultData.class);
        return result;
    }

    @PostMapping
    public String getInfo(@RequestBody List<Map<String, String>> baseInfo) throws IOException {
//        url = "http://127.0.0.1:8011/api/login";
//        Map<String, String> map = new HashMap<>();
//        map.put("mobile","13100001112");
//        map.put("password","123456");
        for (int i = 0; i < baseInfo.size(); i++) {//利用请求上下文，缓存key,避免重复查询。只在一个请求中有效。可以避免批量删除、查询等请求。

            FirstHystrixObservableCommand observableCommand = new FirstHystrixObservableCommand(Collections.singletonList(baseInfo.get(i)));
            Observable<String> observable = observableCommand.observe();//toObservable();
            observable.subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {
                    System.out.println("完成");
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("出错：" + throwable.getMessage());
                }

                @Override
                public void onNext(String result) {
                    System.out.println("返回结果为：" + result);
                }
            });
            StringBuffer result = new StringBuffer();
            BlockingObservable<String> blocking = observable.toBlocking();
            blocking.forEach(next -> {
                result.append("\n结果：").append(next);
            });
            System.out.println(observableCommand.isResponseFromCache());
        }
//        for (Map<String, String> params : baseInfo) {//利用请求上下文，缓存key,避免重复查询。只在一个请求中有效。可以避免批量删除、查询等请求。
//            FirstHystrixCommand command = new FirstHystrixCommand(params);
//            //String result =
//            command.execute();
//            System.out.println(command.isResponseFromCache());//true:从缓存中获取结果：避免发送重复请求
//        }

//        return OBJECT_MAPPER.readValue(result, ResultData.class);
        return null;//result.substring(1);
    }
}
