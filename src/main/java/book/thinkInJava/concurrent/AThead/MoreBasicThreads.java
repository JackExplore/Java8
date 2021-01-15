package book.thinkInJava.concurrent.AThead;

public class MoreBasicThreads {

    public static void main(String[] args) {

        for(int i=0; i<5; i++){
            new Thread(new LiftOff(3)).start();
        }
        System.out.println("Waiting for LiftOff :");
    }
}
