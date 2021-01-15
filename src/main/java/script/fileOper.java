package script;

import java.io.File;

public class fileOper {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Administrator\\Desktop\\gao_patent\\img\\");
        File[] files = file.listFiles();

        for(File f : files){
            String oldName = f.getName();
            String newNameString = oldName.substring(oldName.indexOf("(")+1, oldName.indexOf(")"));
            int newNum = Integer.valueOf(newNameString) + 1;
            String newName = "C:\\Users\\Administrator\\Desktop\\gao_patent\\img\\" + newNum + ".jpg";
            f.renameTo(new File(newName));
//            System.out.println(newName);
        }

        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
        }
    }
}
