package extractPDF.extractOperation;

import com.csvreader.CsvReader;
import extractPDF.CSV.WriteCSV;
import extractPDF.config;
import extractPDF.openCV.CurrentPDFOperation;
import extractPDF.util.FileOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.opencv.core.Rect;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static extractPDF.openCV.OpenCVOperation.pdf2Image;

public class SwitchPDF {

    private static WriteCSV writeCSV;

    public static void choose(File file, String out) throws IOException {
        boolean isError = false;
        System.out.println(file.getAbsolutePath());
        String outPath = config.getPngPath(file);
        String tempPath = config.getPngTempPath(file);
        List<Rectangle> res = new ArrayList<>();
        PDDocument pdDocument=PDDocument.load(file);
        int pageNum=pdDocument.getNumberOfPages();
        pdDocument.close();
        File f0 = new File(tempPath + "temp_"+(pageNum-1)+".png");//(pageNum-1)
        File f1 = new File(outPath + "config.txt");
        //如果存在切割图片 无须切割
        if (!f0.exists())
            pdf2Image(file, config.tempSplitPath, config.dip);
        //不存在图片且
        // 不存在配置文件 解析rectangle并写入
        if (!f1.exists()) {
            //全文解写rect
//            List list = CurrentPDFOperation.getPageRectangle(file);
            //前两页解析rect
            List list=CurrentPDFOperation.getFirstTwoPagesRect(file);
            res = CurrentPDFOperation.transferToRect(file, list);
            new File(outPath).mkdirs();
            f1.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(f1));
            for (int j = 0; j < res.size(); j++) {
                Rectangle rect = res.get(j);
                if (rect.height < 0) {
                    isError = true;
                    rect = chooseFromFile(file);
                    res.remove(j);
                    res.add(j, rect);
                }
                writer.write(rect.x + "," + rect.y + "," + rect.width + "," + rect.height + "\n");
            }
            writer.flush();
            writer.close();
        } else//存在配置文件则直接读取
        {
            int line = 0;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f1)));
            String text = null;
            while ((text = reader.readLine()) != null) {
                String str[] = text.split(",");
                //如果记录有误
                if (Integer.parseInt(str[3]) < 0) {
                    isError = true;
                    Rectangle rect = chooseFromFile(file);
                    res.add(line++, rect);
                } else {
                    res.add(line++, new Rectangle(Integer.parseInt(str[0]), Integer.parseInt(str[1]),
                            Integer.parseInt(str[2]), Integer.parseInt(str[3])));
                }
            }
            reader.close();
            //原本配置文件有误 进行替换
            if(isError){
                f1.delete();
                BufferedWriter writer = new BufferedWriter(new FileWriter(f1));
                for (int j = 0; j < res.size(); j++) {
                    Rectangle rect = res.get(j);
                    writer.write(rect.x + "," + rect.y + "," + rect.width + "," + rect.height + "\n");
                }
                writer.flush();
                writer.close();
            }
        }
        //对于错误文件进行拷贝处理
        if(isError){
            FileOperation.copyErrorFile(file);
            FileOperation.copyErrorConfig(f1);
        }
        CurrentPDFOperation.showCurrentLine(file, res);
        SimplyToTxt.commonToTxt(file, out, res);

        //从文件中读取

//        String parentName = file.getParent();
//        int index = parentName.lastIndexOf("\\");
//        parentName = parentName.substring(index + 1);
//        String mag = parentName.substring(0, parentName.length() - 4);
//        String ruleyear=parentName.substring(parentName.length()-4);
//        CsvReader csvReader=new CsvReader("D:\\extract.csv",',', Charset.forName("utf-8"));
//        while(csvReader.readRecord()){
//            String str=csvReader.get(0);
//            String title=str.substring(0,str.length()-4);
//            String year=str.substring(str.length()-4);
//
//            System.out.println(title.equals(mag));
//            if(title.equals(mag)){
//                if(Integer.parseInt(ruleyear)<=Integer.parseInt(year)){
//                    int x=Integer.parseInt(csvReader.get(1));
//                    int y=Integer.parseInt(csvReader.get(2));
//                    int width=Integer.parseInt(csvReader.get(3));
//                    int height=Integer.parseInt(csvReader.get(4));
//                    rect=new Rectangle(x,y,width,height);
//                    SimplyToTxt.commonToTxt(file, out, rect);
//                    break;
//                }
//            }
//        }


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

    private static Rectangle chooseFromFile(File file) throws IOException {
        String parentName = file.getParent();
        int index = parentName.lastIndexOf("\\");
        parentName = parentName.substring(index + 1);
        String mag = parentName.substring(0, parentName.length() - 4);
        String ruleyear = parentName.substring(parentName.length() - 4);
        CsvReader csvReader = new CsvReader(config.rulePath, ',', Charset.forName("utf-8"));
        Rectangle rect = new Rectangle(0, 0, 0, 0);
        while (csvReader.readRecord()) {
            String str = csvReader.get(0);
            String title = str.substring(0, str.length() - 4);
            String year = str.substring(str.length() - 4);


            if (title.equals(mag)) {
                if (Integer.parseInt(ruleyear) <= Integer.parseInt(year)) {
                    int x = Integer.parseInt(csvReader.get(1));
                    int y = Integer.parseInt(csvReader.get(2));
                    int width = Integer.parseInt(csvReader.get(3));
                    int height = Integer.parseInt(csvReader.get(4));
                    rect = new Rectangle(x, y, width, height);
                    break;
                }
            }
        }
        return rect;

    }
}