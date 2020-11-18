package extractPDF.util;

import extractPDF.Config;

import java.io.*;
import java.util.Iterator;

public class FilterEnglishSentence {

    public static void main(String args[]){
        FilterEnglishSentence filterEnglishSentence=new FilterEnglishSentence();
        filterEnglishSentence.filterENGSentence("C:\\Users\\11346\\Desktop\\原txt\\");
    }

    public void filterENGSentence(String filePath) {

        int sentenceNum=0;
        File filess = new File(filePath);

        File[] files = filess.listFiles();
        if (null != files) {
            for (File file : files) {
                if (file.isDirectory()) {
                    filterENGSentence(file.getAbsolutePath());
                } else {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        StringBuilder text = new StringBuilder();
                        String str = "";
                        while (true) {
                            try {
                                if ((str = bufferedReader.readLine()) == null) break;
                                else {
                                    if(str.replaceAll(" ","").startsWith("1Introduction"));
                                    //todo 过滤abstract
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("文件内容读取错误");
                            }
                        }
                        File newFile = new File("C:\\Users\\11346\\Desktop\\过滤后txt\\filter_" + file.getName());
                        try {
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
                            bufferedWriter.write(text.toString());
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("文件打开错误");
                    }
                }
            }
        }
        System.out.println("过滤后句子总数" + sentenceNum);
    }
}
