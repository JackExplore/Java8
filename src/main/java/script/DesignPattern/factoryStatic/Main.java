package script.DesignPattern.factoryStatic;

public class Main {
    public static void main(String[] args) {

        Person p = PersonFactory.createPerson("chinese");
        p.say();
    }
}
