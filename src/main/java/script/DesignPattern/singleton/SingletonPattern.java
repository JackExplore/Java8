package script.DesignPattern.singleton;

/**
 * 单例模式
 */
public class SingletonPattern {

    private static SingletonPattern INSTANCE = new SingletonPattern();

    private SingletonPattern(){}

    public static SingletonPattern getInstance(){
        return INSTANCE;
    }
}
