package script.juc;

public class From1ToZ {


    public static void main(String[] args) throws InterruptedException {

        final Object o = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (o) {
                for (int i = 1; i <= 26; i++) {
                    System.out.print(i);

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
                for (char i = 'A'; i <= 'Z'; i++) {
                    System.out.print(i + " ");

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
