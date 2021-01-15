package test;

class A implements Cloneable{
    String name;
    B b = new B();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
class B{
    int age = 0;
}

public class CloneTest {

    public static void main(String[] args) throws CloneNotSupportedException {

        A aa = new A();
        aa.name = "this is a";
        aa.b.age = 1;

        A bb = (A) aa.clone();
        System.out.println(bb.name);
        System.out.println(bb.b.age);
        System.out.println(aa == bb);
        System.out.println("aa = " + aa);
        System.out.println("bb = " + bb);
        System.out.println(aa.name == bb.name);
        System.out.println(aa.b);
        System.out.println(bb.b);
    }
}
