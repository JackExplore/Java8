package test;

import test.classloader.String;

public class Test {

    volatile int aaa;
    int bbb;

    public static void main(String[] args) {
        Test t = new Test();
        t.aaa = 111;
        t.bbb = 222;
    }

}
