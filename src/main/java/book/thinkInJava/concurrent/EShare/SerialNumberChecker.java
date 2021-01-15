package book.thinkInJava.concurrent.EShare;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class CircularSet{
    int[] array;
    private int len;
    private int index = 0;
    public CircularSet(int size){
        array = new int[size];
        len = size;
        for(int i=0; i<size; i++){
            array[i] = -1;
        }
    }
    public synchronized void add(int i){
        array[index] = i;
        index = ++index % len;
    }

    public synchronized boolean contains(int val){
        for(int i=0; i<len; i++){
            if(array[i] == val) return true;
        }
        return false;
    }
}

public class SerialNumberChecker {

    static volatile boolean flag = true;

    static final int SIZE = 10;

    static CircularSet serials = new CircularSet(1000);

    static ExecutorService exec = Executors.newCachedThreadPool();

    static class SerialChecker implements Runnable {

        @Override
        public void run() {
            while(flag){
                int serial = SerialNumberGenerator.nextSerialNumber();
                if(serials.contains(serial)){
                    System.out.println("Duplicate: " + serial);
                    System.exit(0);
                }
                serials.add(serial);
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0; i<SIZE; i++){
            exec.execute(new SerialChecker());
        }

        /**
         * 以下代码用来验证数组中数值的顺序性
         * ：顺序不一致
         */
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SerialNumberChecker.flag = false;
        exec.shutdownNow();

        for (int i : SerialNumberChecker.serials.array) {
            System.out.println(i + " ");
        }

    }

}
