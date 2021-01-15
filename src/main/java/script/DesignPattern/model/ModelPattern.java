package script.DesignPattern.model;

/**
 * 设计模式：模板方法
 *
 * 它的一些功能在基类中实现，并且其中一个或多个抽象方法在派生类中定义。
 *
 * 设计模式使得你可以把变化封装在代码里。
 */
abstract class ModelPatternSuper {
    public void a(){
        System.out.println("This is a()");
    }
    public void b(){
        System.out.println("This is b()");
    }

    /**
     * 子类中实现的方法
     */
    public abstract void c();
}
public class ModelPattern extends ModelPatternSuper {

    @Override
    public void c() {
        System.out.println("This is impl c()");
    }
}
