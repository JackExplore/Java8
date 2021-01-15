package test;

public class StringT {

    // 通过编译，但是不能运行加载
    // 并且会导致这个包里面的类不能运行 - 因为导致了同一个包引用优先使用此类
    // classloader.String 可以正常使用
    public static void main(StringT[] args) {
        System.out.println("HELLO");
    }
}
