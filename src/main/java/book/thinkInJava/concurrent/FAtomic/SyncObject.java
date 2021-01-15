package book.thinkInJava.concurrent.FAtomic;

/**
 * 任何一个方法都没有因为另一个方法的同步而被阻塞
 */
class DualSynch {
    private Object syncObject = new Object();
    private int count = 5;
    public synchronized void f(){
        for(int i=0; i<count; i++){
            System.out.println("f()");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void g(){
        synchronized (syncObject){
            for(int i=0; i<count; i++){
                System.out.println("G()");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Thread.yield();
            }
        }
    }
}

public class SyncObject {

    public static void main(String[] args) {
        final DualSynch ds = new DualSynch();
        new Thread(()->ds.f()).start();
        ds.g();
    }
}
