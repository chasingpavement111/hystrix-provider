import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author zhangjie
 */
public class ConcurrencyTest {
    private static volatile boolean stopRequested;

    private static long serielNum;

    public static void main(String[] args)
            throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested) {
//                System.out.println(i);
                i++;
            }
        });
        backgroundThread.start();
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }

    @Test
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
    public void obsevable() throws InterruptedException {

        int concurrency = 2;
        Executor executor = Executors.newSingleThreadExecutor();
        ActionRun action = new ActionRun();
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
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
