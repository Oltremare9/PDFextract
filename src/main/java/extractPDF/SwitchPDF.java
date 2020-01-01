package extractPDF;

import com.csvreader.CsvReader;
import extractPDF.CSV.WriteCSV;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class SwitchPDF {

    private static WriteCSV writeCSV;

    public static void choose(File file, String out) throws IOException {

        String parentName = file.getParent();
        int index = parentName.lastIndexOf("\\");
        parentName = parentName.substring(index + 1);
        String mag = parentName.substring(0, parentName.length() - 4);
        String ruleyear=parentName.substring(parentName.length()-4);
        Rectangle rect = null;

        //从文件中读取
        CsvReader csvReader=new CsvReader("D:\\rule.csv",',', Charset.forName("UTF-8"));
        while(csvReader.readRecord()){
            String str=csvReader.get(0);
            String title=str.substring(0,str.length()-4);
            String year=str.substring(str.length()-4);

            if(title.equals(mag)){
                if(Integer.parseInt(year)<=Integer.parseInt(ruleyear)){
                    int x=Integer.parseInt(csvReader.get(1));
                    int y=Integer.parseInt(csvReader.get(2));
                    int width=Integer.parseInt(csvReader.get(3));
                    int height=Integer.parseInt(csvReader.get(4));
                    rect=new Rectangle(x,y,width,height);
                    SimplyToTxt.commonToTxt(file, out, rect);
                    break;
                }
            }
        }




//        switch (MagazineName.valueOf(mag)) {

//            case 财经论丛: {//12年之前已经重做
//                if(Integer.parseInt(parentName.substring(parentName.length()-4))>=2013)
//                    rect=new Rectangle(0,85,1000,660);
//                else
//                    rect=new Rectangle(0,50,1000,650);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//
//                break;
//            }
//
//
//            case 财经问题研究: {
//                System.out.println(mag);
//                rect = new Rectangle(0, 80, 1000, 700);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            }
//
//            case 金融理论与实践://ok
//                System.out.println(mag);
//                rect = new Rectangle(0, 80, 1000, 695);
//                SimplyToTxt.commonToTxt(file, out, rect);
//
//                break;
//
//            case 财经理论与实践: {//ok
//                rect = new Rectangle(0, 70, 1000, 700);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            }
//            case 当代财经:
//                rect=new Rectangle(0,110,1000,650);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 东北财经大学学报:
//            {
//                if(Integer.parseInt(parentName.substring(parentName.length()-4))==2006){
//                    rect=new Rectangle(0,50,1000,700);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }else{
//                    rect=new Rectangle(0,60,1000,710);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }
//                break;
//            }
//            case 国际金融研究:
//                rect = new Rectangle(0, 80, 1000, 680);
//                SimplyToTxt.commonToTxt(file, out, rect);
//
//                break;
//            case 证券市场导报:
//                rect = new Rectangle(0, 60, 1000, 700);
//                SimplyToTxt.commonToTxt(file, out, rect);
//
//
//                break;
//            case 江西财经大学学报:
//                rect = new Rectangle(0, 100, 1000, 675);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 上海财经大学学报:
//                rect = new Rectangle(0, 90, 1000, 675);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 金融发展研究:
//                rect = new Rectangle(0, 85, 1000, 680);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 金融与经济:
//                rect = new Rectangle(0, 100, 1000, 650);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 南方金融:
//                rect = new Rectangle(0, 100, 1000, 660);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 武汉金融:
//                rect = new Rectangle(0, 80, 1000, 640);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 贵州财经大学学报:
//                rect = new Rectangle(0, 80, 1000, 710);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;

//            case 金融理论探索:
//                if(Integer.parseInt(parentName.substring(parentName.length()-4))<2012){
//                    rect=new Rectangle(0,40,1000,700);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }else{
//                    rect=new Rectangle(0,90,1000,680);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }
//                break;
//            case 哈尔滨商业大学学报:
//                if(Integer.parseInt(parentName.substring(parentName.length()-4))<2011) {
//                    rect = new Rectangle(0, 50, 1000, 690);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }else if (Integer.parseInt(parentName.substring(parentName.length()-4))<2013){
//                    rect=new Rectangle(0,95,1000,680);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }else{
//                    rect=new Rectangle(0,90,1000,670);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }
//                break;
//            case 海南金融:
//                if(Integer.parseInt(parentName.substring(parentName.length()-4))<=2011) {
//                    rect = new Rectangle(0, 50, 1000, 660);
//                }else {
//                    rect = new Rectangle(0, 100, 1000, 660);
//                }
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 辽东学院学报:
//                if(Integer.parseInt(parentName.substring(parentName.length()-4))<=2011) {
//                    rect = new Rectangle(0, 50, 1000, 660);
//                }else{
//                    rect = new Rectangle(0, 90, 1000, 680);
//                }
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 地方财政研究:
//                rect = new Rectangle(0, 90, 1000, 670);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 内蒙古财经大学学报:
//                if(Integer.parseInt(parentName.substring(parentName.length()-4))<2007) {
//                    rect = new Rectangle(0, 50, 1000, 690);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }else if (Integer.parseInt(parentName.substring(parentName.length()-4))<=2008){
//                    rect=new Rectangle(0,70,1000,680);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }else{
//                    rect=new Rectangle(0,88,1000,670);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }
//                break;
//            case 青海金融:
//                rect = new Rectangle(0, 85, 1000, 670);
//                SimplyToTxt.commonToTxt(file, out, rect);
//                break;
//            case 金融经济学研究:
//                if (Integer.parseInt(parentName.substring(parentName.length() - 4)) <= 2007) {
//                    rect = new Rectangle(0, 85, 1000, 700);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                } else {
//                    rect = new Rectangle(0, 70, 1000, 670);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }
//                break;
//            case 现代财经天津财经大学学报:
//                if (Integer.parseInt(parentName.substring(parentName.length() - 4)) < 2013) {
//                    rect = new Rectangle(0, 50, 1000, 700);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                } else {
//                    rect = new Rectangle(0, 90, 1000, 665);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }
//                break;
//            case 中央财经大学学报:
//                if (Integer.parseInt(parentName.substring(parentName.length() - 4)) < 2013) {
//                    rect = new Rectangle(0, 45, 1000, 690);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                } else {
//                    rect = new Rectangle(0, 80, 1000, 670);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                }
//                break;
//            case 上海金融:
//                rect = new Rectangle(0, 70, 1000, 690);
//                SimplyToTxt.commonToTxt(file, out, rect);
//
//                break;
//        }
    }


}
