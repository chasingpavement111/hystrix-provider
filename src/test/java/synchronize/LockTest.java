package synchronize;

/**
 * @author zhangjie
 */
public class LockTest {
    public static void main(String[] args) {
        SynchronizedLock.staticMethodA();
        SynchronizedLock.staticMethodAA();
        System.out.println(SynchronizedLock.staticObj);
    }
}
