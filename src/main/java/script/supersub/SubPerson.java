package script.supersub;

/**
 * ����̳и��� - ���Ӻ��� - ģ�巽��
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
