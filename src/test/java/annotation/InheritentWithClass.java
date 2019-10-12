package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface notInherited {
}

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@interface canInherited {
}

@notInherited
@canInherited
interface FatherClass {

    default String name() {
        return "father";
    }
}

class SonClass implements FatherClass {

    @Override
    public String name() {
        return "son";
    }
}

/**
 * @author zhangjie
 */
public class InheritentWithClass {
    public static void main(String[] args) {
        SonClass son = new SonClass();
        son.name();
//        Annotation[] ansArray = son.getClass().getAnnotations();
//        for (Annotation ans : ansArray) {
//            System.out.println(ans);
//        }
    }
}
