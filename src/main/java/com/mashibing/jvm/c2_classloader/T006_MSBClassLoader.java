package com.mashibing.jvm.c2_classloader;

import java.io.*;

/**
 * 自定义类加载器
 */
public class T006_MSBClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        File f = new File("C:\\Users\\Administrator\\IdeaProjects\\Test8\\target\\classes\\" + name.replace(".", "/").concat(".class"));

        if(!f.exists()) return super.loadClass(name);

        try {
            InputStream is = new FileInputStream(f);

            byte[] b = new byte[is.available()];
            is.read(b);
            is.close();
            return defineClass(name, b, 0, b.length);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
        return super.findClass(name); //throws ClassNotFoundException
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        T006_MSBClassLoader l = new T006_MSBClassLoader();
        Class clazz = l.loadClass("com.mashibing.jvm.c2_classloader.Hello");

        System.out.println(clazz.newInstance());

        System.out.println(l.getClass().getClassLoader());
        System.out.println(l.getParent());

    }
}
