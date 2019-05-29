package com.arges.web.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 使用fluent-hc创建http请求
 *
 * @author zhangjie
 */
public class MyHttpCilentUtils {

    public static String getRequest(String url, Map<String,String> paramsMap) throws IOException {
        System.out.println("发送了http请求");
        Request request = Request.Get(url);
        for(Map.Entry<String,String> param : paramsMap.entrySet()){
            request.addHeader(param.getKey(),param.getValue());
        }
        Response response = request.execute();
        return response.returnContent().asString();
    }

    public static String postRequest(String url, Object params) throws IOException {
        System.out.println("发送了http请求");
        String requestBody;
        if(params instanceof String){
            requestBody = String.valueOf(params);
        }else{
            ObjectMapper objectMapper = new ObjectMapper();
            requestBody = objectMapper.writeValueAsString(params);
        }
        return Request.Post(url).bodyString(requestBody, ContentType.APPLICATION_JSON).execute().returnContent().asString();
    }

    public final static void main(String[] args) throws Exception {

        String url = "http://127.0.0.1:8011/api/user/one";
        Map<String, String> map = new HashMap<>();
        map.put("mobile", "13100001112");
        map.put("password", "123456");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(map);
        String result = Request.Post(url).bodyString(requestBody, ContentType.APPLICATION_JSON).execute().returnContent().asString();
        System.out.println(requestBody);
        System.out.println(Request.Get(url).execute().returnContent().asString());
    }
}
