package com.mashibing.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OSFile {

    static String path1 = "/testData1.file";
    static String path2 = "/testData2.file";
    static byte[] data = new String("123456789").getBytes();

    public static void testBasicFileIO() throws Exception{
        File file = new File(path1);
        FileOutputStream out = new FileOutputStream(file);
        while(true){
//            Thread.sleep(10);
            out.write(data);
        }
    }

    /**
     * ²âÊÔ buffer ÎÄ¼þ IO
     * jvm 8kb  syscall write(8kb byte[])
     *
     */
    public static void testBufferedFileIO() throws Exception{
        File file = new File(path2);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        while(true){
//            Thread.sleep(10);
            out.write(data);
        }
    }

    public static void whatByteBuffer(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("positition:");
    }

    public static void main(String[] args) throws Exception{
        Runnable runBasic = new Runnable() {
            @Override
            public void run() {
                try {
                    testBasicFileIO();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable runBuffered = new Runnable() {
            @Override
            public void run() {
                try {
                    testBufferedFileIO();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(runBasic);
        exec.execute(runBuffered);

        TimeUnit.SECONDS.sleep(10);
        System.out.println("OK");
        System.exit(0);
    }
}
