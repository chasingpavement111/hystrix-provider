import java.io.IOException;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import com.javamex.classmexer.MemoryUtil;

/**
 *
 *
 * @author zhangjie
 *
 */
public class JMHTest {

    private char[] charsPublic;
    //    static int c;
//    int a;
//    long b;
    private String chars;

    public static void main(String[] args) throws IOException {
//        Instrumentation instrumentation = new InstrumentationImpl();
//        long objectSize = Instrumentation.getObjectSize(new String(""));

        JMHTest testObjectSize = new JMHTest();
//        testObjectSize.charsPublic = new char[]{'a', 'b', 'c'};
//         打印对象的shallow size
        System.out.println("Shallow Size: " + MemoryUtil.memoryUsageOf(new Long(1)) + " bytes");
        System.out.println("Shallow Size: " + MemoryUtil.memoryUsageOf(new String("123456")) + " bytes");
        // 打印对象的 retained size
        System.out.println("Retained Size: " + MemoryUtil.deepMemoryUsageOf(new Long(1)) + " bytes");
        System.out.println("Retained Size: " + MemoryUtil.deepMemoryUsageOf(new String("123456")) + " bytes");
//        System.out.println("Retained Size: " + MemoryUtil.deepMemoryUsageOf(testObjectSize, MemoryUtil.VisibilityFilter.NONE) + " bytes");
//        System.out.println("Retained Size: " + MemoryUtil.deepMemoryUsageOf(testObjectSize, MemoryUtil.VisibilityFilter.PRIVATE_ONLY) + " bytes");
//        System.out.println("Retained Size: " + MemoryUtil.deepMemoryUsageOf(testObjectSize, MemoryUtil.VisibilityFilter.NON_PUBLIC) + " bytes");
//        System.out.println("Retained Size: " + MemoryUtil.deepMemoryUsageOf(testObjectSize, MemoryUtil.VisibilityFilter.ALL) + " bytes");
//        System.in.read();
    }

    @Test
    public void classLayout() {
//        <dependency>
//            <groupId>org.openjdk.jol</groupId>
//            <artifactId>jol-core</artifactId>
//            <version>0.9</version>
//        </dependency>
//        System.out.println(VMSupport.vmDetails());
//        System.out.println(ClassLayout.parseInstance(new String("123")).toPrintable());
//        char[] c = new char[]{'1', '2', '3'};
////        synchronized (c) {
////        }
//        System.out.println(ClassLayout.parseInstance(c).toPrintable());
//        System.out.println(ClassLayout.parseInstance(true).toPrintable());
//        byte a = 1;
//        int b = 1;
//        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        System.out.println(ClassLayout.parseClass(Long.class).toPrintable());
        System.out.println(ClassLayout.parseClass(long.class).toPrintable());
    }
}
