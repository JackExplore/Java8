package com.mashibing.juc.c000;

public class T03_Sleep_Yield_Join {

    static void testJoin() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
//                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
//                System.out.println("This is T2 Start...");
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("This is T2 End.");
        });

        // 线程的几种状态 6种
        Thread t3 = new Thread(()->{
            String state = "";
            String showState = "";
            while(true){
                state = t2.getState().toString();
                if(state.equals(showState)){
                    continue;
                }else{
                    showState = state;
                    System.out.println(showState);
                }
            }
        });
        t3.setDaemon(true);
        t3.start();
        Thread.sleep(100);
        t1.start();
        t2.start();
    }

    public static void main(String[] args) throws Exception{
        testJoin();
    }
}
