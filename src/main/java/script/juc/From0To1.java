package script.juc;


/**
 * 深入理解 notify 和 wait：
 * wait 释放锁
 * notify 通知 wait 等待队列，但不释放锁
 */
public class From0To1 {

    public static void main(String[] args) throws InterruptedException {

        final Object o = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (o) {
                for (int i = 1; i <= 100; i = i + 2) {
                    System.out.println(i);

                    o.notify();
                    try {
                        o.wait();       // wait 要回来继续执行，要拿到这把锁，才能继续执行
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notifyAll();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (o) {
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 2; i <= 100; i = i + 2) {
                    System.out.println(i);

                    o.notify();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                o.notifyAll();
            }
        }, "t2");

        t2.start();
        t1.start();

    }
}
