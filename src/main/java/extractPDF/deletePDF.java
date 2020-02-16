package extractPDF;

import extractPDF.extractOperation.SwitchPDF;
import extractPDF.util.FileOperation;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class deletePDF {
    public static void main(String args[]) throws IOException {
        File file = new File("D:\\LDA\\金融论文\\");
        readFile(file.getAbsolutePath());
    }

    static int count = 1;

    public static void readFile(String path) throws IOException {

//        System.out.println("ssss");
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        readFile(file2.getAbsolutePath());
                    } else {
                        if (file2.getName().contains("须知")) {
                            System.out.println(file2.getName());
                            if (file2.delete())
                                System.out.println("删除成功" + count++);
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
}
