/**
 * @author zhangjie
 */
interface AnonymousInterFace {
    public void anonyMethod(String authorName);
}

abstract class AbstractClass {
    abstract public void abstractMethod(String authorName);
}

class Enclosing_Class {
    static private int static_enclosing_i;
    private int nonstatic_enclosing_i;

    /*static*/ {

        int[] local_var = {1};

        /**
         * 本地方法：可以实现多个接口，或者继承一个类。不允许既实现接口、同时又继承类。
         * 可以定义在静态或非静态的 方法或代码块 中，代码块一般位于方法内
         * 不可包含静态成员
         * 可访问外围类的静态和非静态成员
         * 不可包含访问修饰符，因为不可作为外围类的成员，作用范围如同局部变量。
         * 当需要创建多余一个内部类对象时，或者需要一个构造器或者需要重载构造器时，因选择本地内部类，而不是匿名内部类
         */
        class LocalClass implements Cloneable, AnonymousInterFace {

            private int i;

            @Override
            public void anonyMethod(String authorName) {
                authorName += "s";
                System.out.println("this is the implementation from local class, defined by author " + authorName + i);
            }

            private void local_method(String var) {
                var += "s";
                local_var[0] = 2;
                System.out.println(var);
                System.out.println(static_enclosing_i);
                System.out.println(nonstatic_enclosing_i);
                nonstatic_enclosing_i = 2222;//todo
                nonstatic_methodOne();
                //或写成:Enclosing_Class.this.nonstatic_methodOne();
                Enclosing_Class.static_method();
            }
        }
        System.out.println("创建定义在非静态代码块中的实现了多接口的本地类");
        LocalClass localInstanceOne = new LocalClass();
        localInstanceOne.anonyMethod("ayowei");
        localInstanceOne.local_method("a");

        class LocalClassTwo extends AbstractClass {
            @Override
            public void abstractMethod(String authorName) {
                System.out.println("this is the concrete definition from local class, defined by author " + authorName);
            }
        }
        System.out.println("创建定义在非静态代码块中的继承了一个类的本地类");
        LocalClassTwo localInstanceTwo = new LocalClassTwo();
        localInstanceTwo.abstractMethod("ayowei");
    }

    public static void main(String[] args) {
        Static_Memeber_Class.static_method();
        Static_Memeber_Class static_memeber_class = new Static_Memeber_Class(1);
        static_memeber_class.nonstatic_method();

        Enclosing_Class enclosing_class = new Enclosing_Class();

        Nonstatic_Memeber_Class nonstatic_memeber_class = enclosing_class.new Nonstatic_Memeber_Class(2);//非静态成员需要持有对外围类的秘密引用，所以生成内部类实例前需要先生成外部类的实例
        nonstatic_memeber_class.nonstatic_method();

        enclosing_class.nonstatic_methodOne();
    }

    static private void static_method() {
        System.out.println("static method of enclosing class");
    }

    private void nonstatic_methodOne() {
        /**
         * 匿名类：接口或抽象函数的直接实现
         * 可以定义在静态或非静态的 方法或代码块 中，代码块一般位于方法内
         * 不可包含静态成员
         * 可访问外围类的静态和非静态成员
         * 不具有构造函数
         */
        AnonymousInterFace anony = new AnonymousInterFace() {
            @Override
            public void anonyMethod(String authorName) {
                System.out.println("this is the implementation, defined by author " + authorName + "\\n可访问外围类的成员：" + static_enclosing_i + nonstatic_enclosing_i);
            }
        };
        System.out.println("接口的匿名类实例");
        anony.anonyMethod("ayowei");

        AbstractClass abstr = new AbstractClass() {
            @Override
            public void abstractMethod(String authorName) {
                System.out.println("this is the concrete definition, defined by author " + authorName);
            }
        };
        System.out.println("抽象类的匿名类实例");
        abstr.abstractMethod("ayowei");

        /**
         * lamba表达式，现在常用于替换匿名类
         */
        System.out.println("利用lamba实现接口创建的实例");
        AnonymousInterFace anonyTwo = authorName -> System.out.println("this is the implementation of Lamba expresion, defined by author " + authorName);
        anonyTwo.anonyMethod("ayowei");
    }

    /**
     * 静态成员类
     * 可以定义静态、非静态成员
     * 只能访问外围类的静态成员
     */
    static class Static_Memeber_Class {
        static private int static_i;
        private int nonstatic_i;

        Static_Memeber_Class(int i) {
            System.out.println("执行静态成员的构造函数");
            static_i = static_enclosing_i;
            nonstatic_i = i;
        }

        static void static_method() {
            System.out.println("执行静态成员的静态函数");
            System.out.println(static_i);
            System.out.println(static_enclosing_i);
        }

        void nonstatic_method() {
            System.out.println("执行静态成员的非静态函数");
            System.out.println(static_i);
            System.out.println(nonstatic_i);
            System.out.println(static_enclosing_i);
        }
    }

    /**
     * 非静态成员类
     * 只可以包含非静态成员（非静态成员变量、非静态成员方法）
     * 可以访问外围类的静态和非静态成员
     */
    class Nonstatic_Memeber_Class {
        private int nonstatic_i;//不可包含静态成员

        Nonstatic_Memeber_Class(int i) {
            System.out.println("执行非静态成员的构造函数");
            if (i > 0) {
                nonstatic_i = static_enclosing_i;
            } else {
                nonstatic_i = nonstatic_enclosing_i;
            }
        }

        void nonstatic_method() {//不可包含静态方法
            System.out.println("执行非静态成员的非静态函数");
            System.out.println(Static_Memeber_Class.static_i);//可调用外围类中定义的其他内部类的静态成员
            System.out.println(nonstatic_i);
            System.out.println(static_enclosing_i);//用下面两种方式调用外围类的成员同样有效
            //System.out.println(Enclosing_Class.this.static_enclosing_i);
            //System.out.println(Enclosing_Class.static_enclosing_i);
        }
    }
}
