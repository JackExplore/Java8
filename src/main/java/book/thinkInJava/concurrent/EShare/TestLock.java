package book.thinkInJava.concurrent.EShare;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

    public String testAAA(){
        Lock lock = new ReentrantLock();
        try{
            lock.lock();
            System.out.println("AAA lock()");
            return "OK";
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new TestLock().testAAA();
    }
}
