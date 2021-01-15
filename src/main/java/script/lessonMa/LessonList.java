package script.lessonMa;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class LessonList {

    public static String getSource(String URI) {
        String res = new String();
        try {
            File resFile = new File(URI);
            FileInputStream fileInputStream = new FileInputStream(resFile);
            byte[] bytes = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            while(fileInputStream.read(bytes) != -1){
                stringBuilder.append(new String(bytes, "UTF-8"));
            }
            res = stringBuilder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }

        return res;
    }

    public static void parse(String res){
        Document document = Jsoup.parse(res);
        Elements list0 = document.getElementsByClass("details-ctn").get(0).getAllElements();
        Elements list = list0.get(0).getElementsByClass("detail-item-ctn");
        for(Element ele : list){

            Element indexEle = ele.getElementsByClass("index").get(0);
            String index = indexEle.text();

            Element sectionEle = ele.getElementsByClass("section-title").get(0);
            String sectionTitle = sectionEle.text();

            Element subEle = ele.getElementsByClass("sub-title").get(0);
            String subTitle = subEle.text();

            System.out.println(index + "\t" + subTitle + "\t" + sectionTitle);
        }
        System.out.println();
    }
    public static void main_1(String[] args) throws IOException {
        String fileName = "C:\\Users\\Administrator\\Desktop\\课程表\\tencent.lesson";
        String source = getSource(fileName);
        parse(source);
    }
    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\Administrator\\Desktop\\课程表\\tencent.lesson";
        String source = getSource(fileName);
        System.out.println(source);
    }
}
