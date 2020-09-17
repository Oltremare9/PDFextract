package extractPDF;

import java.io.*;

public class CutSentence {
    static int wordNum=0;
    public static void main(String args[]) {
        CutSentence cutSentence = new CutSentence();
        cutSentence.cutSentenct("C:\\Users\\11346\\Desktop\\原txt\\");
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
                                    text.append(str);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("文件内容读取错误");
                            }
                        }
                        str = text.toString();
                        str.replaceAll("\n", "");
                        String sentence[] = str.split("。");
                        for (String s : sentence) {
                            s=s.replaceAll(" ","");
                            s=s.trim();
                            wordNum+=s.length();
                            res.append(s);
                            res.append("。");
                            res.append("\n");
                        }
                        File newFile = new File("C:\\Users\\11346\\Desktop\\切分后txt\\CUT"+file.getName());
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
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("文件打开错误");
                    }
                }
            }
        }
        System.out.println("单词总数"+wordNum);
    }
}