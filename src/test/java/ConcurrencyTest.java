import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author zhangjie
 */
public class ConcurrencyTest {
    private static volatile boolean stopRequested;//设置为volatile,否则不能保证能够及时发现main线程中的stopRequest的值改变。

    private static long serielNum;

//    public static void main(String[] args)
//            throws InterruptedException {
//        Thread backgroundThread = new Thread(() -> {
//            int i = 0;
//            while (!stopRequested) {
////                System.out.println(i);
//                i++;
//            }
//        });
//        backgroundThread.start();
//        TimeUnit.SECONDS.sleep(1);
//        stopRequested = true;
//    }

    @Test
//    public void testForExecutor() {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "开始测试：executor为局部变量时，其任务是否能离开作用域后继续执行");
        doExecute();
        System.out.println("结束测试");
    }

    private static void doExecute() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            System.out.println("Executor：" + Thread.currentThread().getName() + "开始执行");
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Executor结束执行");
        });
        ((ExecutorService) executor).shutdown();
    }

    //    @Test
    public void makeNotautomicOperationAutomic() throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            while (!stopRequested) {
                System.out.println("one");
                System.out.println(selftGain());
            }
        });
        Thread threadTwo = new Thread(() -> {
            while (!stopRequested) {
                System.out.println("two");
                System.out.println(selftGain());
            }
        });
        threadOne.start();
        threadTwo.start();
        TimeUnit.NANOSECONDS.sleep(10);
        stopRequested = true;
    }

    private synchronized long selftGain() {
        return serielNum++;
    }

    @Test
    public void test() {
        System.out.println((byte) 3);//3的补码：0000 0011
        System.out.println(~((byte) 3));//3的补码取反：1..1 1100, 进行求负数的原码运算：1_( ~111 1100 + 1) = 原码 0b10000100 = 十进制数 -4
        System.out.println((byte) 0b0000_0000);//转为byte,使无符号数变为有符号数
        System.out.println((byte) 0b1000_0000);//-128
        System.out.println((byte) 0b1000_0001);//-127
        System.out.println((byte) 0b1111_1111);//-1
    }

    @Test
    public void obsevable() throws InterruptedException {
        int concurrency = 2;
        Executor executorOne = Executors.newSingleThreadExecutor();
        Executor executorTwo = Executors.newSingleThreadExecutor();
        Executor fixedExecutor = Executors.newFixedThreadPool(2);
        ActionRun action = new ActionRun();
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            fixedExecutor.execute(() -> {
                ready.countDown(); // Tell timer we're ready
                try {
                    start.await(); // Wait till peers are ready
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown(); // Tell timer we're done
                }
            });

//            executorOne.execute(() -> {
//                ready.countDown(); // Tell timer we're ready
//                try {
//                    start.await(); // Wait till peers are ready
//                    action.run();
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                } finally {
//                    done.countDown(); // Tell timer we're done
//                }
//            });
//            executorTwo.execute(() -> {
//                ready.countDown(); // Tell timer we're ready
//                try {
//                    start.await(); // Wait till peers are ready
//                    action.run();
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                } finally {
//                    done.countDown(); // Tell timer we're done
//                }
//            });
        }
        ready.await(); // Wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown(); // And they're off!
        done.await(); // Wait for all workers to finish
        System.out.println(System.nanoTime() - startNanos);
    }
}

class ActionRun implements Runnable {
    @Override
    public void run() {
        System.out.println("执行Runnable任务");
    }
}
