package script.supersub;

/**
 * 子类继承父类 - 钩子函数 - 模板方法
 */
public class SubPerson extends Person{

    @Override
    public void doWhat(){
        super.doWhat();
//        System.out.println("SubPerson.doWhat()");
    }

    @Override
    public void doSomeThing(){
        System.out.println("SubPerson.doSomeThing()");
    }

    public static void main(String[] args) {
        Person p = new SubPerson();
        p.doWhat();
    }
}
