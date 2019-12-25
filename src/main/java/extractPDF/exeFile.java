package extractPDF;

import extractPDF.CSV.WriteCSV;

import java.io.File;
import java.util.LinkedList;

public class exeFile {
    static SwitchMag switchMag=new SwitchMag();
    public static void readFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        readFile(file2.getAbsolutePath());

                    } else {
//                        System.out.println("文件:" + file2.getAbsolutePath());
//                        System.out.println("文件:" + file2.getName());
                        String parentName = file2.getParent();
                        int index = parentName.lastIndexOf("\\");
                        parentName = parentName.substring(index + 1);
                        SwitchPDF.choose(file2,"D:\\LDA\\金融论文txt\\" + parentName + "\\");

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
                        switchMag.choose(file2,writeCSV);
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
                            switchMag.choose(file2,writeCSV);
                        }
                    }
                }
            } else {
                System.out.println("文件不存在!");
            }
        }


    public static void main(String args[]) {

        long start = System.currentTimeMillis();

//        readFile("D:\\LDA\\金融论文\\金融经济学研究");
        WriteCSV writeCSV=new WriteCSV("D:\\金融经济学研究.xls");
        extractFile("D:\\LDA\\金融论文txt\\",writeCSV);
        writeCSV.write();
        writeCSV.close();
        long end = System.currentTimeMillis();
        System.out.println("start time:" + start+ "; end time:" + end+ "; Run Time:" + (end - start)/1000 + "(s)   "+ (end - start)/60000+ "（mins）"+((end - start)/1000)%60+"s");


    }
}
