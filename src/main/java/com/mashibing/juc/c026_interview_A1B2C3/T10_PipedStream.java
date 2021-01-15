package com.mashibing.juc.c026_interview_A1B2C3;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 酷炫的写法
 */
public class T10_PipedStream {

    public static void main(String[] args) throws IOException {

        char[] aI = "123456789".toCharArray();
        char[] aC = "ABCDEFGHI".toCharArray();

        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();

        PipedOutputStream output1 = new PipedOutputStream();
        PipedOutputStream output2 = new PipedOutputStream();

        input1.connect(output2);
        input2.connect(output1);

        String msg = "Your Turn";

        new Thread(() -> {
            byte[] buffer = new byte[9];
            try {
                for (char c : aI) {
                    input1.read(buffer);
                    if(new String(buffer).equals(msg)){
                        System.out.print(c);
                    }
                    output1.write(msg.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            byte[] buffer = new byte[9];
            try {
                for (char c : aC) {
                    System.out.print(c);
                    output2.write(msg.getBytes());
                    input2.read(buffer);
                    if(new String(buffer).equals(msg)){
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
