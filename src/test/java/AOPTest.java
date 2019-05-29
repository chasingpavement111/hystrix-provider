import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import com.arges.web.controller.FooManager;
import com.arges.web.service.FooAOPService;
import com.arges.web.service.impl.FooAOPServiceImpl;

/**
 * 
 *  
 * @author zhangjie
 * 
 */
public class AOPTest {

    public static void  main(String[] args){
//        FooAOPService service = new FooAOPServiceImpl();
//        service.defaultMethod();
//        ((FooAOPServiceImpl) service).finalMethod();

        //        Proxy.newProxyInstance(FooAOPService.class.getClassLoader(),new Class[]{FooAOPService.class},handler);

        AspectJProxyFactory factory = new AspectJProxyFactory(new FooAOPServiceImpl());//jdk dynamic proxy
        factory.setInterfaces(FooAOPService.class);
//        factory.setTargetClass(FooAOPServiceImpl.class);//NPE???
        factory.setExposeProxy(true);
        FooAOPService service = factory.getProxy();
//        FooAOPServiceImpl impl = factory.getProxy();//报错
        service.defaultMethod();

        AspectJProxyFactory managerFactory = new AspectJProxyFactory(new FooManager());//cglib proxy
        FooManager manager = managerFactory.getProxy();
        System.out.println(managerFactory.toString());
        System.out.println(manager.toString());
        manager.printMessage();




//        Object obj = AopContext.currentProxy();
//        System.out.println(obj.toString());

    }

    @Test
    public void getDeclaredClass(){
        Class[] array = First.class.getDeclaredClasses();
        System.out.println(array);
        System.out.println();
    }
}


class First{
    class Second{}

    class Third{
        class Four{}
    }
}