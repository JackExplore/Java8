package book.thinkInJava.concurrent.test;


import java.util.concurrent.TimeUnit;

class ARun implements Runnable{

    BSyn b = new BSyn();

    @Override
    public void run() {
        b.f();
    }
}

class BSyn {
    public BSyn(){
        synchronized (this){
            System.out.println("BSync BSync()...create()");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    synchronized void f(){
        System.out.println("BSync f()");
    }
}

public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new ARun());
        t.start();
        TimeUnit.SECONDS.sleep(3);
        t.interrupt();
    }

}
