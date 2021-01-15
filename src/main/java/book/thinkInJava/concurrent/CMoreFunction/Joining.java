package book.thinkInJava.concurrent.CMoreFunction;

class Sleeper extends Thread {
    private int duration;
    public Sleeper(String name, int sleepTime){
        super(name);
        duration = sleepTime;
        start();
    }
    public void run(){
        try{
            sleep(duration);
        }catch (InterruptedException e){
            System.out.println(getName() + " was Interrupted,  isInterrupted() = " + isInterrupted());
            return;
        }
        System.out.println(getName() + " has awakened.");
    }
}
class Joiner extends Thread {
    private Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper){
        super(name);
        this.sleeper = sleeper;
        start();
    }
    public void run(){
        try {
            sleeper.join();
        }catch (InterruptedException e){
            System.out.println("Joiner Interrupted.");
        }
        System.out.println(getName() + " join completed.");
    }
}

public class Joining {

    public static void main(String[] args) {
        Sleeper s1 = new Sleeper("s1", 1500);
        Sleeper s2 = new Sleeper("s2", 1500);

        Joiner j1 = new Joiner("j1", s1);
        Joiner j2 = new Joiner("j2", s2);

        s1.interrupt();

    }
}
