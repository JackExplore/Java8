package book.thinkInJava.concurrent.JCooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * 参考这里示例：不论哪个线程先启动，不会影响执行过程！  标志位 waxOn = true/false;
 *
 * IMPORTANT !!!
 */
class Car {
    private boolean waxOn = false;

    public synchronized void waxed() {
        waxOn = true;   // Ready to buff
        notifyAll();
    }

    public synchronized void buffed() {
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitForWaxing() throws InterruptedException {
        while (waxOn == false) {
            wait();
        }
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn == true) {
            wait();
        }
    }
}

class WaxOn implements Runnable {
    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println("Wax On");
                TimeUnit.SECONDS.sleep(2);      // sleep interrupt
                car.waxed();
                car.waitForBuffing();                   // wait interrupt
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Exiting via interrupt.  - WaxOn");
        }
        System.out.println("Ending Wax On task");
    }
}

class WaxOff implements Runnable {

    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.waitForWaxing();

                System.out.println("Wax Off");
                TimeUnit.SECONDS.sleep(2);

                car.buffed();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Exiting via interrupt.  - WaxOff");
        }
        System.out.println("Ending Wax Off task.");
    }
}


public class WaxOMatic {

    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();

        exec.execute(new WaxOff(car));
        TimeUnit.SECONDS.sleep(10);
        exec.execute(new WaxOn(car));



        TimeUnit.SECONDS.sleep(10);
        exec.shutdownNow(); // Interrupt all tasks
    }
}
