package synchronize;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author zhangjie
 */
public class ThreadSubClass extends Thread {

    public static void main(String[] args) {
        Thread one = new Thread(new ThreadSubClass());
        Thread two = new Thread(() -> {
            one.interrupt();
//            try {
//                one.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("two end");
        });
        one.start();
        two.start();
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("sleep end");

    }

    @Override
    public void run() {
        System.out.println("start running");
        synchronized (this) {
            try {
                wait();
                System.out.println("wait end");
            } catch (InterruptedException e) {
//            e.printStackTrace();
                try {
                    System.out.println("sleep 1s");
                    sleep(1L);
                    System.out.println("sleep end");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test_ScheduledThreadExcutor() throws InterruptedException {
        ScheduledExecutorService scheduledThread = Executors.newScheduledThreadPool(1);
        scheduledThread.scheduleWithFixedDelay(() -> {
            System.out.println(System.nanoTime());
            if (this instanceof ScheduledExecutorService) {
                System.out.println("yes");
            }
        }, 2L, 1L, TimeUnit.SECONDS);
        scheduledThread.scheduleAtFixedRate(() -> {
            System.out.println("at start");
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("at end");
        }, 3L, 1L, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(5L);
        Thread.currentThread();
        scheduledThread.shutdown();
        System.out.println("结束");
    }
}
