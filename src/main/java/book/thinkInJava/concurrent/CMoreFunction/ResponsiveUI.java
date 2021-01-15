package book.thinkInJava.concurrent.CMoreFunction;

class UnresponsiveUI {
    private volatile double d = 1;
    public UnresponsiveUI() throws Exception{
        while(d > 0){
            d = d + (Math.PI + Math.E) / d;
        }
        System.in.read();   // Never gets here
    }
}

public class ResponsiveUI extends Thread {

    private static volatile double d = 1;

    public ResponsiveUI(){
        setDaemon(true);    // 计算确实在作为后台程序运行，同时还在等待用户输入
        start();
    }

    public void run(){
        while(true){
            d = d + (Math.PI + Math.E) / d;
        }
    }

    public static void main(String[] args) throws Exception{
//        new UnresponsiveUI();
        new ResponsiveUI();
        System.in.read();
        System.out.println(d);
    }
}
