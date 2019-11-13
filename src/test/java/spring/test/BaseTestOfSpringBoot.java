package spring.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.arges.web.HystrixProviderApplication;

/**
 * @author zhangjie
 */
/*
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>2.1.4.RELEASE</version>
            <scope>test</scope>
        </dependency>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HystrixProviderApplication.class})
class BaseTestOfSpringBoot {
}
