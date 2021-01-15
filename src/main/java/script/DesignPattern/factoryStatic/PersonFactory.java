package script.DesignPattern.factoryStatic;

public class PersonFactory {

    public static Person createPerson(String tag) {
        if ("chinese".equalsIgnoreCase(tag)) {
            return new Chinese();
        } else if ("american".equalsIgnoreCase(tag)) {
            return new American();
        } else {
            return null;
        }
    }
}
