import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author zhangjie
 */
public class RateLimitOfGuavaTest {
    private static final RateLimiter rateLimiter = RateLimiter.create(1);

    public static void main(String[] args) {
        int times = 3;
        int order = times;
        while (order > 0) {
            order--;
            int current = times - order;
            Thread thread = new Thread(() -> {
                System.out.print("第" + current + "次，执行开始时间：" + System.nanoTime() + "，执行结果：");
                requestCall();
            });
            thread.start();
            try {
                TimeUnit.MILLISECONDS.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void requestCall() {
        if (rateLimiter.tryAcquire()) {
            System.out.println("通过");
            return;
        }
        System.out.println("被限流");
    }
}
