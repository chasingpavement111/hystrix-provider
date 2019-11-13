package spring.test;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.arges.web.HystrixProviderApplication;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HystrixProviderApplication.class)
@AutoConfigureMockMvc
public class MockMvcBaseTestOfSpringBoot {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMockUsage() throws Exception {
        // 初始化数据
        ObjectMapper jsonMapper = new ObjectMapper();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobile", "13100001111");
        requestBody.put("password", "123456");
        String requestBodyStr = jsonMapper.writeValueAsString(requestBody);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("result_desc", "登录成功");
        String responseBodyStr = jsonMapper.writeValueAsString(responseBody);
        // 发送mock请求
        String returnContent = mockMvc.perform(MockMvcRequestBuilders.post("/shop/request/mock")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBodyStr))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseBodyStr))
                .andReturn().getResponse().getContentAsString();
        System.out.println(returnContent);
    }

    @Test
    public void contextLoads() {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat formatter = mapper.getDateFormat();
        DeserializationConfig deserializer = mapper.getDeserializationConfig();
        DeserializationConfig cfg = mapper.getDeserializationConfig();
        deserializer.getDateFormat();
        SerializationConfig serializaer = mapper.getSerializationConfig();
        System.out.println("结束");
    }

}
