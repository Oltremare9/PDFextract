package extractPDF.Magazines;

import com.google.common.base.CharMatcher;
import extractPDF.CSV.ReadCSV;
import jxl.read.biff.BiffException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AllMagazines {
    public static int num=0;
    private String title;
    private String author;
    private void  match(File file) throws IOException {
        String parent=file.getParent();
        int index=parent.lastIndexOf("\\");
        parent=parent.substring(index+1);
        String res[]=new String[2];
        String filename=file.getName();
        filename.substring(0,filename.length()-4);
        index=filename.lastIndexOf("_");
        String aut=filename.substring(index+1,filename.length()-4);
        filename=filename.substring(0,index);
        filename=filename.replaceAll("_","");

        if(filename.contains("省略")) {
            filename = filename.replaceAll("省略", "");

            String first = filename.substring(0, 3);
            String end = filename.substring(filename.length() - 3);

            //匹配excel文件中标题
            ReadCSV readCSV = new ReadCSV("D:\\match.csv");
            try {
                res = readCSV.matchCsv(first, end, parent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            title = res[0];
            author = res[1];
        }else{
            title=filename;
            author=aut;
        }
    }
    public String extractTitle(File file) throws IOException {
        /*
        String res = "";
        if (file.isFile()) {
            String s = "";
            String temp = "";

            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                res += s;
                temp = CharMatcher.WHITESPACE.trimFrom(temp);
                temp = CharMatcher.WHITESPACE.replaceFrom(temp, "");
                while ((s = re.readLine()) != null) {
                    if (s.startsWith("(")) {
                        res.replaceAll("＊", "");
                        res.replaceAll("\\*", "");
//                        System.out.println(res);
                        break;
                    } else {
                        res += temp;
                        temp = s;
                    }
                }
                break;
            }

        }
        return res;
        */
//        String filename=file.getName();
//        filename.substring(0,filename.length()-4);
//        int index=filename.lastIndexOf("_");
//        String aut="";
//        if(index>0) {
//             aut= filename.substring(index + 1);
//        }
//        filename=filename.substring(0,index);
//        filename.replaceAll("_","");
//        filename.replaceAll("省略","");
//        if(filename.length()>10){
//            String first=filename.substring(0,5);
//            String end=filename.substring(filename.length()-5);
//            //匹配excel文件中标题
//            ReadCSV readCSV=new ReadCSV("D:\\match.xlsx");
//            try {
//                String res[]=readCSV.matchCsv(first,end,aut);
//            } catch (BiffException e) {
//                e.printStackTrace();
//            }
//        }else{
//            return "";
//        }
        match(file);
        return title;
    }

    public String extractAuthor(File file) throws IOException {
//        String temp = "";
//        temp = file.getName();
//        int index = temp.lastIndexOf("_");
//        if (index > 0) {
//            temp = temp.substring(index + 1);
//            temp = temp.substring(0, temp.length() - 4);
//        }
//        return temp;
        return author;
    }

    public String extractKeyword(File file) throws IOException {
        String res = "";
        if (file.isFile()) {

            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                s.replaceAll(" ","");
//                s = s.trim().replaceAll("\\ +", "");
//                System.out.println(s);
                if (s.contains("关键词")) {
//                        System.out.println(s+"----------");
                    res += s;
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                        s.replaceAll(" ","");
//                        s=s.replaceAll("\\ +","");
                        if (!s.startsWith("中图") && !s.startsWith("["))
                            res += s;
                        else
                            break;
                    }
                    res = res.substring(4, res.length());
//                    System.out.println(res);
                    break;
                }
            }

        }
        return res;
    }

    public String extractAbstract(File file) throws IOException {
        String res = "";
        if (file.isFile()) {

            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                s.replaceAll(" ","");
//                s = s.trim().replaceAll("\\ +", "");
//                System.out.println(s);
                if (s.startsWith("摘要") || s.contains("摘要")) {
//                        System.out.println(s+"----------");
                    res += s;
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                        CharMatcher.WHITESPACE.replaceFrom(s," ");
//                        s.replaceAll(" ","");
//                        s=s.replaceAll("\\ +","");
                        if (!s.contains("关键词"))
                            res += s;
                        else
                            break;
                    }
//                    System.out.println(res);
                    break;
                }
            }

        }
//        return res.substring(3);
        if (res.length() > 3)
            res = res.substring(3);
        return res;
    }

    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String> result = new ArrayList<String>();
        if (file.isFile()) {
            String res = "";
            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                if (s.contains("参考文献")) {
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                        res += s;
                    }
                    String str[] = res.split("\\[\\d+\\]");
                    for (int i = 0; i < str.length - 1; i++) {
                        if (str[i].equals(""))
                            continue;
                        if (!str[i].startsWith("•")) {
//                            System.out.println(str[i]);
                            result.add(str[i]);
                        } else {
                            char c[] = str[i].toCharArray();
                            for (int num = 1; num < c.length; num++) {
                                if (c[num] == '•') {
                                    String temp = str[i].substring(num + 1);
//                                    System.out.println(temp);
                                    result.add(temp);
                                    break;
                                }
                            }
                        }

//                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    }
                    char temp[] = str[str.length - 1].toCharArray();
                    int num = 0;
                    for (int i = 0; i < temp.length; i++) {
                        if (temp[i] == '.')
                            num = i;
                    }
                    str[str.length - 1].substring(0, num + 1);
                    result.add(str[str.length - 1]);
                    break;
                }
            }
            if (result.size() >= 1) {
                String str = result.get(result.size() - 1);
                String resstr = "";
                while (true) {
                    int index = str.indexOf(".");
                    if (index == -1 || index == str.length() - 1) {
                        resstr += str;
                        break;
                    }
                    char c1 = str.charAt(index + 1);
                    if ((c1 <= 'Z' && c1 >= 'A') || (c1 <= 'z' && c1 >= 'a')) {
                        break;
                    } else {
//                        System.out.println(file.getName());
//                        System.out.println(index);
                        resstr += str.substring(0, index);

                        str = str.substring(index + 1);
                    }
                }
                if (resstr.contains("责任") || resstr.contains("特约")) {
//                    System.out.println(resstr);
                    int index = Math.max(resstr.indexOf("责任"), resstr.indexOf("特约"));

                    resstr = resstr.substring(0, index - 1);
//                    System.out.println(resstr);
                }
                num+=result.size();

            }
            //删除过长的引文
//            ArrayList<String> temp=new ArrayList<>();
//            for(int i=0;i<result.size();i++){
//                if(result.get(i).length()<100)
//                    temp.add(result.get(i));
//                else{
//                    error++;
//                    System.out.println("删除过长引文"+result.get(i));
//                }
//            }
//
//            System.out.println("引文数量"+num+"   因长度限制删去引文"+error);
//            return temp;
            return result;
        }
        return result;
    }

    public String extractForeword(File file) throws IOException {
        String res = "";
        if (file.isFile()) {
            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                if (s.contains("一、") || s.contains("引言")) {
                    res += s;
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                        if (!s.contains("二、"))
                            res += s;
                        else
                            break;
                    }
                    res = res.substring(4, res.length());
//                    System.out.println(res);
                    break;
                }
            }

        }
        return res;
    }
}

//    public static void main(String args[]) throws IOException {
//        File file=new File("D:\\LDA\\金融论文txt\\东北财经大学学报2006\\东北三省高等职业教育的现状与发展_陈国辉.txt");
//        ArrayList<String> result = new ArrayList<String>();
//        if (file.isFile()) {
//            String res = "";
//            String s = "";
//            BufferedReader re = new BufferedReader(new FileReader(file));
//            while ((s = re.readLine()) != null) {
//                s = CharMatcher.WHITESPACE.trimFrom(s);
//                s=CharMatcher.WHITESPACE.replaceFrom(s,"");
//                if (s.contains("参考文献")) {
//                    while ((s = re.readLine()) != null) {
//                        s = CharMatcher.WHITESPACE.trimFrom(s);
//                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                        res += s;
//                    }
//                    String str[] = res.split("\\[\\d+\\]");
//                    for (int i = 0; i < str.length-1; i++) {
//                        if(str[i].equals(""))
//                            continue;
//                        if (!str[i].startsWith("•")) {
////                            System.out.println(str[i]);
//                            result.add(str[i]);
//                        }
//                        else {
//                            char c[] = str[i].toCharArray();
//                            for (int num = 1; num < c.length; num++) {
//                                if (c[num] == '•') {
//                                    String temp = str[i].substring(num + 1);
////                                    System.out.println(temp);
//                                    result.add(temp);
//                                    break;
//                                }
//                            }
//                        }
//
////                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//                    }
//                    char temp[]=str[str.length-1].toCharArray();
//                    int num=0;
//                    for(int i=0;i<temp.length;i++){
//                        if(temp[i]=='.')
//                            num=i;
//                    }
//                    str[str.length-1].substring(0,num+1);
//                    result.add(str[str.length-1]);
//                    break;
//                }
//            }
//            if(result.size()>=1) {
//                String str = result.get(result.size() - 1);
//                String resstr = "";
//                while (true) {
//                    int index = str.indexOf(".");
//                    if(index==-1 || index==str.length()-1){
//                        resstr+=str;
//                        break;
//                    }
//                    char c1 = str.charAt(index + 1);
//                    if ((c1 <= 'Z' && c1 >= 'A')||(c1 <= 'z' && c1 >= 'a')) {
//                        resstr+=str.substring(0,index+1);
//                        break;
//                    } else {
//                        System.out.println(file.getName());
//                        System.out.println(index);
//                        resstr += str.substring(0, index);
//
//                        str = str.substring(index + 1);
//                    }
//                }
//                if(resstr.contains("责任")||resstr.contains("特约")){
//                    System.out.println(resstr);
//                    int index=Math.max(resstr.indexOf("责任"),resstr.indexOf("特约"));
//
//                    resstr=resstr.substring(0,index-1);
//                    System.out.println(resstr);
//                }
//                result.remove(result.size() - 1);
//                result.add(resstr);
//            }
//
//        }
//        System.out.println("-------------------------------------------------------");
//        for(int i=0;i<result.size();i++){
//            System.out.println(result.get(i));
//        }
//    }
//}