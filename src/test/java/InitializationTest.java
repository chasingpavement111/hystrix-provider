/**
 * 
 *  
 * @author zhangjie
 * 
 */
public class InitializationTest {

    private static String a = "a";
    private static String b;
    private static String e;

    static   {
        System.out.println("初始化e\\f");
//        f="f";
        b="b";
//          c = "c";
    }

    private String c = "c";
    private String d;
    private String f;


    private InitializationTest() {
        System.out.println("初始化c\\d");

        e="e";
        d = "d";
    }

    public static void main(String[] agrs) {
//
//        System.out.println("打印值：\na="+a
//                +"\nb="+b+"\ne="+e);
        System.out.println(InitializationTest.a);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println(InitializationTest.b);
        System.out.println(InitializationTest.e);
//        InitializationTest initialization = new InitializationTest();
        System.out.println("打印值：\n");
//                +"a="+a+"\nb="+b+"\ne="+e
//                +"\nc="+initialization.c+"\nd="+initialization.d+"\nf="+initialization.f);
    }
}
