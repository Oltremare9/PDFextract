package extractPDF;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SimplyToTxt {
    //提取对用过滤区域的文本 使用rectangle来控制对应区域
    static void toText(File file, String out, Rectangle rec, int start, int end) throws IOException {
        if (file.isFile()) {
            PDDocument document = PDDocument.load(file);
            int pageAmount = document.getNumberOfPages();
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
//                Rectangle rect = new Rectangle(0, 80, 2000, 200);
            Rectangle rect = rec;
            stripper.addRegion("head", rect);
            String cut = "";
            System.out.println(file.getName());
            if (pageAmount >= 3) {
                for (int page = Math.max(start, 0); page < Math.min(pageAmount, end); page++) {
//                for (int page = 0; page < 1; page++) {
                    System.out.println(page);
                    try {
                        stripper.extractRegions(document.getPage(page));
                    }
                    catch (Exception e){
                        System.out.println(file.getName());
                        break;
                    }
                    String temp = stripper.getTextForRegion("head");
                    byte[] b = null;
                    for (int c = 0; c < temp.length(); c++) {
                        String s = temp.substring(c, c + 1);
                        if(s.equals("　")) {
                            cut += " ";
                            continue;
                        }
                        b = s.getBytes("unicode");
                        if (b[2] == -1) {
                            b[3] = (byte) (b[3] + 32);
                            b[2] = 0;
                            cut += (new String(b, "unicode"));
                        } else {
                            cut += s;
                        }
                    }
                }
                File temp = new File(out);
                if (!temp.exists()) {
                    temp.mkdir();
                }
                File filetxt = new File(out + file.getName().substring(0, file.getName().length() - 4) + ".txt");
                FileOutputStream f = new FileOutputStream(filetxt, false);
                f.write(cut.getBytes());
                f.close();
                document.close();
            }
        }
    }

    public static void topToTxt(File file, String out) {
        Rectangle rect = new Rectangle(0, 80, 2000, 400);
        try {
            toText(file, out, rect, 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void bottomToTxt(File file, String out) {
        Rectangle rect = new Rectangle(0, 400, 2000, 1000);
        try {
            toText(file, out, rect, 0, Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void commonToTxt(File file, String out,Rectangle rectangle) {
        try {
            //过滤重复pdf文件
            String name=file.getName();
            name=name.substring(0,name.length()-4);
            if(!name.endsWith(")"))
                toText(file, out, rectangle, 0, Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
