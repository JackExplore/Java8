package book.thinkInJava.concurrent.FAtomic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ExplicitPairManager1 extends PairManager {
    Lock lock = new ReentrantLock();
    @Override
    public synchronized void increment() {
        lock.lock();
        try{
            p.incrementX();
            p.incrementY();
            store(getPair());
        }finally {
            lock.unlock();
        }
    }
}

class ExplicitPairManager2 extends PairManager {
    Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        Pair temp;
        lock.lock();
        try{
            p.incrementY();
            p.incrementX();
            temp = getPair();
        }finally {
            lock.unlock();
        }
        store(temp);
    }
}

/**
 * 这里出现异常，因为使用 lock 锁定，和 synchronized 不是同一把锁!
 */
public class ExplicitCriticalSection {

    public static void main(String[] args) {
        PairManager pman1 = new ExplicitPairManager1();
        PairManager pman2 = new ExplicitPairManager2();
        CriticalSection.testApproaches(pman1, pman2);
    }
}
