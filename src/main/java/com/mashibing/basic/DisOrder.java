package com.mashibing.basic;

/**
 * ָ������
 * CPU ָ�������ִ��
 */
public class DisOrder {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws Exception{
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;y = 0;
            a = 0;b = 0;

            Thread one = new Thread(()->{
                a = 1;
                x = b;
            });
            Thread other = new Thread(()->{
                b = 1;
                y = a;
            });
            one.start();other.start();
            one.join();other.join();

            String result = "�� " + i + " �� (" + x + ", " + y + ")";
            if(x==0 && y==0){
                System.err.println(result);
                break;
            }
        }
    }
}
