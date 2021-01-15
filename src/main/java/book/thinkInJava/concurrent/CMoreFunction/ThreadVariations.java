package book.thinkInJava.concurrent.CMoreFunction;

class ThreadMethod {

    private int countDown = 5;

    private Thread t;       // this thread

    private String name;

    public ThreadMethod(String name){
        this.name = name;
    }

    public void taskThread(){
        if(t == null){
            t = new Thread(name){       // 匿名内部类的形式
                @Override
                public void run() {
                    System.out.println("This is taskThread..." + this.toString() + " / " + Thread.currentThread().getName());
                }

                public String toString(){
                    return getName() + " : " + countDown;
                }
            };
            t.start();
        }
    }
}

public class ThreadVariations {
    public static void main(String[] args) {
        new ThreadMethod("ThreadMethod").taskThread();
    }
}
