package onJava8.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GetChannel {

    private static String name = "123.java";
    private static final int BSIZE = 1024;

    public static void main(String[] args) {

        // 写入一个文件
        try (FileChannel fc = new FileOutputStream(name).getChannel()){
            fc.write(ByteBuffer.wrap("Some text ".getBytes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 在文件尾添加
        try(FileChannel fc = new RandomAccessFile(name, "rw").getChannel()){
            fc.position(fc.size());             // 移动到结尾
            fc.write(ByteBuffer.wrap("some more".getBytes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取文件 e
        try(FileChannel fc = new FileInputStream(name).getChannel()){
            ByteBuffer buff = ByteBuffer.allocate(BSIZE);
            fc.read(buff);
            buff.flip();
            while(buff.hasRemaining()){
                System.out.write(buff.get());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.flush();
    }
}







