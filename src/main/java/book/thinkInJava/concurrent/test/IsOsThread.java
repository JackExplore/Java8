package book.thinkInJava.concurrent.test;

import java.util.concurrent.TimeUnit;

/**
 * 多线程是在OS级别创建了多线程吗
 * 不是
 */
public class IsOsThread {

    public static void main(String[] args) throws Exception{
        Thread a = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(60);
                System.out.println("This is thread aaa");
            }catch (Exception e){
            }
        });


        Thread b = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(60);
                System.out.println("This is thread bbb");
            }catch (Exception e){
            }
        });



        Thread c = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(60);
                System.out.println("This is thread ccc");
            }catch (Exception e){
            }
        });
        Thread d = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(60);
                System.out.println("This is thread ddd");
            }catch (Exception e){
            }
        });
        Thread e = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(60);
                System.out.println("This is thread eee");
            }catch (Exception f){
            }
        });

        a.start();
        b.start();
        c.start();
        d.start();
        e.start();

    }
}
