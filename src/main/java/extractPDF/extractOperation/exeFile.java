package extractPDF.extractOperation;

import extractPDF.CSV.WriteCSV;
import extractPDF.util.FileOperation;
import extractPDF.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class exeFile {
    static SwitchMag switchMag = new SwitchMag();
    static int count = 0;

    public static void readFile(String path, String out, Pair pair) throws IOException {

//        System.out.println("ssss");
        File file = new File(path);
        PDDocument pdDocument;
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        readFile(file2.getAbsolutePath(), out, pair);
                    } else {
                        System.out.println("正在处理"+file2.getAbsolutePath());
                        //存在无法打开 丢失root的pdf
                        try {
                            pdDocument = PDDocument.load(file2);
                        }catch (IOException e){
                            continue;
                        }
                        if (pdDocument.getNumberOfPages() < 2)
                            continue;
                        pdDocument.close();
                        if (file2.getName().length() < 10)
                            continue;
//                        System.out.println("文件:" + file2.getAbsolutePath());
//                        System.out.println("文件:" + file2.getName());
                        String parentName = file2.getParent();
                        int index = parentName.lastIndexOf("\\");
                        parentName = parentName.substring(index + 1);
                        try {
                            SwitchPDF.choose(file2, out + "\\" + parentName + "\\",pair);
                            FileOperation.copyFile(file2);
                            if (file2.delete()) {
                                System.out.println("删除成功");
                                System.out.println("已处理" + count++ + "篇");
                            } else {
                                System.out.println("删除失败");
                                System.out.println("已处理" + count++ + "篇");
                            }
                        } catch (IOException e) {

                        }

                    }
                }
            }
            if (file.isFile()) {
                pdDocument = PDDocument.load(file);
                //文件页数需要大于1
                if (pdDocument.getNumberOfPages() > 1) {
                    pdDocument.close();
                    if (file.getName().length() > 10) {

                        String parentName = file.getParent();
                        int index = parentName.lastIndexOf("\\");
                        parentName = parentName.substring(index + 1);
                        try {
                            SwitchPDF.choose(file, out + "\\" + parentName + "\\",pair);
                            FileOperation.copyFile(file);
                            if (file.delete()) {
                                System.out.println("删除成功");
                                System.out.println("已处理" + count++ + "篇");
                            } else {
                                System.out.println("删除失败");
                                System.out.println("已处理" + count++ + "篇");
                            }
                        } catch (IOException e) {
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    public static void extractFile(String path, WriteCSV writeCSV) {
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    list.add(file2);
                } else {
//                        System.out.println("文件:" + file2.getAbsolutePath());
                    switchMag.choose(file2, writeCSV);
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                            System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                    } else {
//                            System.out.println("文件:" + file2.getAbsolutePath());
                        switchMag.choose(file2, writeCSV);
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }


//    public static void extract() {
//
//        long start = System.currentTimeMillis();
//        readFile("D:\\LDA\\金融论文\\上海金融","D:\\LDA\\金融论文txt\\");
//        WriteCSV writeCSV=new WriteCSV("D:\\上海金融.xls");
//        extractFile("D:\\LDA\\金融论文txt\\",writeCSV);
//        writeCSV.write();
//        writeCSV.close();
//        long end = System.currentTimeMillis();
//        System.out.println("start time:" + start+ "; end time:" + end+ "; Run Time:" + (end - start)/1000 + "(s)   "+ (end - start)/60000+ "（mins）"+((end - start)/1000)%60+"s");
//
//
//    }
}
