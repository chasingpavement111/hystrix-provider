import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yowei.web.entity.UserInfo;
import com.yowei.web.service.UserInfoService;

/**
 * 
 *  
 * @author zhangjie
 * 
 */
public class dubboConsumerTest {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:consumer.xml"});
        context.start();
        UserInfoService userService = (UserInfoService)context.getBean("userService"); // 获取远程服务代理
        UserInfo hello = userService.getOneById(1L); // 执行远程方法
        System.out.println( hello ); // 显示调用结果
    }
}
