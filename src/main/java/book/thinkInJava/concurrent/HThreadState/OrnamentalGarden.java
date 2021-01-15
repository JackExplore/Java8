package book.thinkInJava.concurrent.HThreadState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Count {
    private int count = 0;
    private Random rand = new Random(47);

    public synchronized int increment() {
        int temp = count;

        // do sth.
        Thread.yield();
//        try {
//            TimeUnit.MILLISECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return (count = ++temp);    // 注意这里的写法
    }

    public synchronized int value() {
        return count;
    }

}

class Entrance implements Runnable {

    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<Entrance>();
    // Atomic operation on a volatile field:
    private static volatile boolean canceled = false;

    public static void cancel() {
        canceled = true;
    }

    private int number = 0;
    // doesn't need sync to read:
    private final int id;

    public Entrance(int id) {
        this.id = id;
        // keep this task in a list.
        // Also prevents garbage collection of dead tasks:
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++number;
            }
            System.out.println(this + " Total: " + count.increment());  // : 注意这里并不是同步更新的
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Stopping " + this); // ?
    }

    public synchronized int getValue() {
        return number;
    }

    public String toString() {
        return "Entrance " + id + " : " + getValue();
    }

    public static int getTotalCount() {
        return count.value();
    }

    public static int sumEntrances() {
        int sum = 0;
        for (Entrance en : entrances) {
            sum += en.getValue();
        }
        return sum;
    }
}

public class OrnamentalGarden {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();

        if(!exec.awaitTermination(5000, TimeUnit.MILLISECONDS)){
            System.out.println("Some tasks were not terminated!");
        }

        System.out.println("Total : " + Entrance.getTotalCount());
        System.out.println("Sum of Entrance : " + Entrance.sumEntrances());
    }
}









