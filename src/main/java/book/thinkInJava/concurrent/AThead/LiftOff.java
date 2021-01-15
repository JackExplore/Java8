package book.thinkInJava.concurrent.AThead;

/**
 * 实现了 Runnable 更像是一个任务
 */
public class LiftOff implements Runnable{

    protected int countDown = 10;       // Default

    private static int taskCount = 0;   // Task Count

    private final int id = taskCount++; // Task ID

    public LiftOff(){}

    public LiftOff(int countDown){
        this.countDown = countDown;
    }

    public String status(){
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + "),";
    }

    @Override
    public void run() {
        while(countDown-- > 0){
            System.out.println(status());
            Thread.yield();
        }
    }
}
