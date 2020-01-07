package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LocalFinanceResearch extends AllMagazines{
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
                if (s.startsWith("内容提要") || s.contains("提要")) {
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
        if (res.length() > 5)
            res = res.substring(5);
        return res;
    }

    @Override
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
                    String str[] = res.split("\\〔\\d+\\〕");
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
                if (resstr.contains("编辑") ) {
//                    System.out.println(resstr);
                    int index = resstr.indexOf("编辑");

                    resstr = resstr.substring(0, index - 3);
                    result.remove(result.size()-1);
                    result.add(resstr);
//                    System.out.println(resstr);
                }
                num+=result.size();

            }

            return result;
        }
        return result;
    }

    @Override
    public String extractText(File file) throws IOException {
        String res = "";
        if (file.isFile()) {

            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                if(s.contains("一、"))
                    break;
            }
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                if(s.contains("参考文献")||s.contains("责任编辑"))
                    break;
                else if (s.contains("表\\d")||s.contains("图\\d")||s.contains("作者简介")||
                        s.contains("DOI")||s.contains("关键词")||s.contains("中图")
                        ||s.contains("项目基金"))  {
                    continue;
                }else{
                    res+=s;
                }

            }

        }
        return res;
    }
}
