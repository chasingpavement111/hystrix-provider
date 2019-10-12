package DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface One {
    void print();

    void println();
}

/**
 * @author zhangjie
 */
public class jdkDynamicProxy implements InvocationHandler {

    private Object object;

    private jdkDynamicProxy(Object object) {
        this.object = object;
    }

    //    @Test
//    public void testPolyMorphism() {
    public static void main(String[] args) {
        One a = (One) Proxy.newProxyInstance(One.class.getClassLoader(), new Class[]{One.class}, new jdkDynamicProxy(new Two()));
        System.out.println(a.toString());
//        One obj = new OneImp();
//        obj.print();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(object, args);
    }

}

class OneImp implements One {

    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public void println() {
        System.out.println("\n换行");
    }

    protected void accessLevel() {
        System.out.println("protect can has transaction");
    }
}

class Two extends OneImp {
    @Override
    public void print() {
        System.out.println("this is two");
    }

    @Override
    protected void accessLevel() {
        System.out.println("this is two");
        super.accessLevel();
    }
}

