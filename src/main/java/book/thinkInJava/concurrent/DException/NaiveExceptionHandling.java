package book.thinkInJava.concurrent.DException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NaiveExceptionHandling {

    public static void main(String[] args) {

        try{
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
        }catch (RuntimeException e){
            // this statement will ?
            System.out.println("Execution has been handled@");
        }
    }
}
