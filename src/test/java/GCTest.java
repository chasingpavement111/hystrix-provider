import org.junit.Test;

/**
 * @author zhangjie
 */
public class GCTest {

    @Test
    //-verbose:gc -XX:+PrintGCDetails -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:PretenureSizeThreshold=3145728
    //  -XX:+DoEscapeAnalysis -verbose:gc -XX:+PrintGCDetails -XX:ParallelGCThreads=2
    public void printGCDetailLog() {
        long start = System.currentTimeMillis();
        obj();
        System.out.println(System.currentTimeMillis() - start);
    }

    private void obj() {
        for (int i = 0; i < 1000000000; i++) {
            byte[] buffer = alloc();
        }
    }

    private byte[] alloc() {
        byte[] buffer = new byte[2];
        buffer[0] = 1;
        return buffer;
    }
}
