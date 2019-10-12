package com.arges.web.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;
import rx.observables.BlockingObservable;

import com.arges.web.hystrix.FirstHystrixCommand;
import com.arges.web.hystrix.FirstHystrixObservableCommand;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangjie
 */
@RestController
@RequestMapping
public class RequestController {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @GetMapping
    public String getInfo(@RequestParam Map<String, String> baseInfo) throws IOException {
        try {
            TimeUnit.SECONDS.sleep(2L);
            return "ok";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @GetMapping("/ipInfo")
    public String getResquestInfo(HttpServletRequest request) {
        List<String> IP_HEADERS = Arrays
                .asList("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR",
                        "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR");
        for (String ipHeader : IP_HEADERS) {
            String ip = request.getHeader(ipHeader);
            if (ip != null && !ip.isEmpty() && !ip.equalsIgnoreCase("unknown")) {
                System.out.println(ipHeader + "=" + ip);
//				return ip;
            }
        }
//		return request.getRemoteAddr();
        System.out.println();
        //todo
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            System.out.println(name + "=" + request.getHeader(name));
        }
        System.out.println();
        try {
            InetAddress inet = InetAddress.getLocalHost();
            System.out.println("hostAddress=" + inet.getHostAddress());
            System.out.println("canonicalHostName=" + inet.getCanonicalHostName());
            System.out.println("hostName=" + inet.getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("localAddr=" + request.getLocalAddr());
        System.out.println("remoteAddr=" + request.getRemoteAddr());

        System.out.println();
//        RemoteIpValve remoteIpValve = new RemoteIpValve();
//        System.out.println(remoteIpValve.getRemoteIpHeader() + "=" + request.getHeader(remoteIpValve.getRemoteIpHeader()));
//        System.out.println(remoteIpValve.getProxiesHeader() + "=" + request.getHeader(remoteIpValve.getProxiesHeader()));
//        System.out.println("InternalProxies=" + remoteIpValve.getInternalProxies());
//        System.out.println("TrustedProxies=" + remoteIpValve.getTrustedProxies());
//        System.out.println("ProtocolHeader=" + remoteIpValve.getProtocolHeader());
//        System.out.println("HttpsValue=" + remoteIpValve.getProtocolHeaderHttpsValue());
//        System.out.println("HttpServerPort=" + remoteIpValve.getHttpServerPort());

        // 获取本机ip
        try {
            Enumeration<NetworkInterface> network = NetworkInterface.getNetworkInterfaces();
            System.out.println();
            System.out.println();
            while (network.hasMoreElements()) {
                NetworkInterface inet = network.nextElement();
                System.out.println(inet.getHardwareAddress());
                Enumeration<InetAddress> inetAddress = inet.getInetAddresses();
                while (inetAddress.hasMoreElements()) {
                    System.out.println();
                    System.out.println(inetAddress.nextElement());
                }
                List<InterfaceAddress> interAddress = inet.getInterfaceAddresses();
                interAddress.forEach(inter -> System.out.println(inter.getAddress() + ":" + inter.getBroadcast() + ":" + inter.getNetworkPrefixLength()));
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
