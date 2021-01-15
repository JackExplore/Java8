package onJava8.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GetChannel {

    private static String name = "123.java";
    private static final int BSIZE = 1024;

    public static void main(String[] args) {

        // д��һ���ļ�
        try (FileChannel fc = new FileOutputStream(name).getChannel()){
            fc.write(ByteBuffer.wrap("Some text ".getBytes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ���ļ�β���
        try(FileChannel fc = new RandomAccessFile(name, "rw").getChannel()){
            fc.position(fc.size());             // �ƶ�����β
            fc.write(ByteBuffer.wrap("some more".getBytes()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ��ȡ�ļ� e
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







