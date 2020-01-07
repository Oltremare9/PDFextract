package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ResearchonInternationalFinancialandEconomic extends AllMagazines{

    @Override
    public String extractKeyword(File file) throws IOException {
        return super.extractKeyword(file);
    }

    @Override
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
                if (s.contains("内容摘要")||s.contains("内容提要")) {
//                        System.out.println(s+"----------");
                    res += s;
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                        CharMatcher.WHITESPACE.replaceFrom(s," ");
//                        s.replaceAll(" ","");
//                        s=s.replaceAll("\\ +","");
                        if (!s.contains("关键词")&&!s.contains("中图"))
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
        if(res.length()>5)
            res=res.substring(5);
        return res;
    }

    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {

        return super.extractQuatation(file);
    }

    @Override
    public String extractForeword(File file) throws IOException {
        String res = "";
        if (file.isFile()) {
            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                if (s.contains("一、")||s.contains("引言")) {
                    res += s;
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                        if (!s.contains("二、")&&!s.contains("一、")&&!s.contains("内容"))
                            res += s;
                        else
                            break;
                    }
//                    res = res.substring(4, res.length());
//                    System.out.println(res);
                    break;
                }
            }

        }
        return res;
    }

    @Override
    public String extractText(File file) throws IOException {
        String res = "";
        if (file.isFile()) {

            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));

            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                if(s.contains("参考文献")||s.contains("责任编辑"))
                    break;
                else if (s.contains("表\\d")||s.contains("图\\d")) {
                    continue;
                }else{
                    res+=s;
                }

            }

        }
        return res;
    }
}
