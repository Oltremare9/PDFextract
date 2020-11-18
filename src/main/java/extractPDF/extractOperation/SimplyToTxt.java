package extractPDF.extractOperation;
import extractPDF.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimplyToTxt {
    //提取对用过滤区域的文本 使用rectangle来控制对应区域
    static void toText(File file, String out, List<Rectangle> rec, Pair pair) throws IOException {
        if (file.isFile()) {
            PDDocument document = PDDocument.load(file);
            int pageAmount = document.getNumberOfPages();
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
//                Rectangle rect = new Rectangle(0, 80, 2000, 200);

            String cut = "";
            System.out.println(file.getName());
            if (pageAmount >= 3) {
                //不为空 则限定前后各取几页 为空则默认全文
                if(null==pair) {
                    for (int page = 0; page < pageAmount; page++) {
                        System.out.println(page);
                        try {
                            Rectangle rect = rec.get(page);
                            stripper.addRegion("head", rect);
                            stripper.extractRegions(document.getPage(page));
                        } catch (Exception e) {
                            System.out.println(file.getName());
                            break;
                        }
                        String temp = stripper.getTextForRegion("head");
                        byte[] b = null;
                        for (int c = 0; c < temp.length(); c++) {
                            String s = temp.substring(c, c + 1);
                            if (s.equals("　")) {
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
                    //限定前后页数
                }else{
                    for (int page = 0; page < pair.getStart(); page++) {
                        System.out.println(page);
                        try {
                            Rectangle rect = rec.get(page);
                            stripper.addRegion("head", rect);
                            stripper.extractRegions(document.getPage(page));
                        } catch (Exception e) {
                            System.out.println(file.getName());
                            break;
                        }
                        String temp = stripper.getTextForRegion("head");
                        byte[] b = null;
                        for (int c = 0; c < temp.length(); c++) {
                            String s = temp.substring(c, c + 1);
                            if (s.equals("　")) {
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
                    for (int page = Math.max(pair.getStart(),pageAmount-pair.getEnd());
                        page < pageAmount; page++) {
                        System.out.println(page);
                        try {
                            Rectangle rect = rec.get(page);
                            stripper.addRegion("head", rect);
                            stripper.extractRegions(document.getPage(page));
                        } catch (Exception e) {
                            System.out.println(file.getName());
                            break;
                        }
                        String temp = stripper.getTextForRegion("head");
                        byte[] b = null;
                        for (int c = 0; c < temp.length(); c++) {
                            String s = temp.substring(c, c + 1);
                            if (s.equals("　")) {
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
            document.close();
        }
    }

//    public static void topToTxt(File file, String out) {
//        Rectangle rect = new Rectangle(0, 80, 2000, 400);
//        try {
//            toText(file, out, rect, 0, 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void bottomToTxt(File file, String out) {
//        Rectangle rect = new Rectangle(0, 400, 2000, 1000);
//        try {
//            toText(file, out, rect, 0, Integer.MAX_VALUE);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void commonToTxt(File file, String out, List<Rectangle> rectangle, Pair pair) {
        try {
            if(null==rectangle || rectangle.size()==0){
                rectangle=new ArrayList<Rectangle>();
                int pageAmount=PDDocument.load(file).getNumberOfPages();
                for(int i=0;i<pageAmount;i++){
                    rectangle.add(new Rectangle(0,0,2000,2000));
                }
            }
            //过滤重复pdf文件
            String name=file.getName();
            name=name.substring(0,name.length()-4);
            if(!name.endsWith(")"))
                toText(file, out, rectangle,pair);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
