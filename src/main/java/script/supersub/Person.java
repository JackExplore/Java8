package script.supersub;

public class Person {


    public void doWhat(){
        System.out.println("Person.doWhat()");
        doSomeThing();
    }

    public void doSomeThing(){
        System.out.println("Person.doSomeThing()");
    }
}
