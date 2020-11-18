package extractPDF.util;

import java.io.*;

public class CutENGSentence {
    static int wordNum = 0;

    public static void main(String args[]) {
        CutENGSentence cutSentence = new CutENGSentence();
        cutSentence.cutSentenct("C:\\Users\\11346\\Desktop\\related work\\");
    }

    public void cutSentenct(String filePath) {

        File filess = new File(filePath);

        File[] files = filess.listFiles();
        if (null != files) {
            for (File file : files) {
                if (file.isDirectory()) {
                    cutSentenct(file.getAbsolutePath());
                } else {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        StringBuilder res = new StringBuilder();
                        StringBuilder text = new StringBuilder();
                        String str = "";
                        while (true) {
                            try {
                                if ((str = bufferedReader.readLine()) == null) break;
                                else {
                                    if(str.endsWith("-")){
                                        str=str.substring(0,str.length()-1);
                                    }else{
                                        str+=" ";
                                    }
                                    text.append(str);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("文件内容读取错误");
                            }
                        }
                        str = text.toString();
                        str.replaceAll("\n", "");
                        String sentence[] = str.split("\\.");
                        for (String s : sentence) {
                            String words[] = s.split(" ");
                            wordNum += words.length;
                            char startChar = ' ';
                            if (s.length() > 1) {
                                startChar = s.charAt(0);
                            }
                            if (startChar == ' ' || (startChar >= 'A' && startChar <= 'Z')) {
                                res.append("\n");
                            }
                            res.append(s.trim());
                            res.append(".");
                        }
                        sentence=res.toString().split("\n");
                        res=new StringBuilder();
                        for(String s:sentence) {
                            res.append(s);
                            if (!s.endsWith("al.") && !s.endsWith("i.e.")) {
                                res.append("\n");
                            }

                            File newFile = new File("C:\\Users\\11346\\Desktop\\切分后txt\\CUT" + file.getName());
                            try {
                                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
                                bufferedWriter.write(res.toString());
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
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("文件打开错误");
                    }
                }
            }
        }
        System.out.println("单词总数" + wordNum);
    }
}