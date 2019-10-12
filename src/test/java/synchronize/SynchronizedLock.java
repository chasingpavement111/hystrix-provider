package synchronize;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangjie
 */
class SynchronizedLock {
    static Object staticObj = new Object();
    private Object obj = new Object();

    public static void main(String[] args) {
        SynchronizedLock a = new SynchronizedLock();
        Thread one = new Thread(() -> a.normal());
        Thread two = new Thread(() -> a.normal());
        one.start();
        two.start();
    }

    static synchronized void staticMethodA() {
        System.out.println("这是静态同步方法，需要获取类锁");
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("退出静态同步方法");
    }

    static synchronized void staticMethodAA() {
        System.out.println("这是静态同步方法，需要获取类锁");
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("退出静态同步方法");
    }

    private void normal() {
        System.out.println(Thread.currentThread().getName() + "开始执行");
        /**
         * 两个线程中的方法尝试获取相同的类锁
         * 所以第一个线程的同步块会阻塞第二个线程执行同步块。
         */
//        synchronized (AClass.class) {
        /**
         * 同一个对象实例，获取的锁对象为同一个成员变量
         * 两个线程中的方法尝试获取相同的对象锁
         * 所以第一个线程的同步块会阻塞第二个线程执行同步块。
         */
//        synchronized (obj) {
        /**
         * 获取的锁对象为lockedNomel方法返回的Object对象（不可返回null）,
         * 因为每次返回的对象都不相同，两个线程中的方法尝试获取不同的对象锁
         * 所以不会阻塞。
         */
        synchronized (lockedNomel()) {
            System.out.println("锁住");
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("释放锁");
        }
        System.out.println(Thread.currentThread().getName() + "开始退出");
    }

    private Object lockedNomel() {
        System.out.println("执行作为锁对象的方法");
        return new Object();
    }

    void staticMethodB() {
        synchronized (SynchronizedLock.class) {
            System.out.println("这是普通方法，包含了需要获取类锁的同步代码块，需要获取类锁");
        }
    }

    synchronized void unstaticMethodA() {
        System.out.println("这是非静态同步方法，需要获取对象锁");
    }


    void unstaticMethodB() {
        synchronized (this) {
            System.out.println("这是普通方法，包含了需要获取对象锁的同步代码块，需要获取对象锁");
        }
    }

    void unstaticMethodC() {
        synchronized (obj) {//同步代码块获取对象锁的另一种方法
            System.out.println("这是普通方法，包含了需要获取对象锁的同步代码块，需要获取对象锁");
        }
    }
}
