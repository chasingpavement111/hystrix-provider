package DynamicProxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @author zhangjie
 */
public class Cglib implements MethodInterceptor {


    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[]{One.class});
        enhancer.setSuperclass(Two.class);
        enhancer.setCallback(new Cglib());
        One obj = (OneImp) enhancer.create();
        obj.print();
        obj.println();
        ((OneImp) obj).accessLevel();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        method.invoke(o, objects);
//        methodProxy.invoke(o, objects);
        return methodProxy.invokeSuper(o, objects);
    }
}