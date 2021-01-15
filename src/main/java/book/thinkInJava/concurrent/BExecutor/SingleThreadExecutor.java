package book.thinkInJava.concurrent.BExecutor;

import book.thinkInJava.concurrent.AThead.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {

    public static void main(String[] args) {

        ExecutorService exec = Executors.newSingleThreadExecutor();
        for(int i=0; i<5; i++){
            exec.execute(new LiftOff(3));
        }

        exec.shutdown();
    }
}
