interface Incrementable{
    void increment();
}

/**
 *
 *
 * @author zhangjie
 *
 */
public class EnclosingClassTest {
    public static void main(String[] args){
//        EnclosingClassTest test = new EnclosingClassTest();


        Callee1 callee1 = new Callee1();
        Callee2 callee2 = new Callee2();
        MyIncrement.f(callee2);

        Caller caller1 = new Caller(callee1);
        Caller caller2 = new Caller(callee2.getCallbackReference());
        caller1.go();
        caller1.go();
        caller2.go();
        caller2.go();
    }

}

class Callee1 implements Incrementable{

    private int i =0;

    @Override
    public void increment() {
        i++;
        System.out.println(i);
    }
}

class MyIncrement{
    static void f(MyIncrement mi){ mi.increment();}

    public void increment(){
        System.out.println("other increment");
    }
}

class Callee2 extends MyIncrement{

    static private int si=0;
    private int i=0;

    @Override
    public void increment() {
        super.increment();
        i++;
        System.out.println(i);
    }

    Incrementable getCallbackReference(){
        return new Closure();
    }

    static class inner{
        static private int io;
        static {io=Callee2.si;}
         public inner(){
             io=Callee2.si;
         }
    }

    private class Closure implements Incrementable{

        @Override
        public void increment() {
            //调用外部类的方法，否则会陷入递归无限循环；？？？
            Callee2.this.increment();
        }
    }
}

class Caller{
    private Incrementable callbackReference;
    Caller(Incrementable incrementable){ callbackReference = incrementable;}
    void go(){callbackReference.increment();}
}