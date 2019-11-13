package spring.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.arges.web.service.KitchenHessianService;

/**
 * 人脸库
 *
 * @author zhangjie
 */
public class KitchenHessianServiceTest extends BaseTestOfSpringBoot {

    @Autowired
    private KitchenHessianService kitchenService;

    private int systemType = -1;
    private int endpointType = -1;

    @Test
    public void pushTransmission() throws Exception {
        List<String> clientIdList = new ArrayList<>();
        String subject = "";
        String content = "";
        boolean success = kitchenService.pushTransmission(systemType, endpointType, clientIdList, subject, content);
        System.out.println("create result: " + success);
    }
}