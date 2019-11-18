package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SecuritiesMarketHerald extends AllMagazines{


    @Override
    public String extractAbstract(File file) throws IOException {
        return super.extractAbstract(file);
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
                s=CharMatcher.WHITESPACE.replaceFrom(s,"");
                if (s.contains("参考文献")) {
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                        res += s;
                    }
                    String str[] = res.split("\\[\\d+\\]");
                    for (int i = 0; i < str.length-1; i++) {
                        if(str[i].equals(""))
                            continue;
                        if (!str[i].startsWith("•")) {
//                            System.out.println(str[i]);
                            result.add(str[i]);
                        }


//                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    }
//                    char temp[]=str[str.length-1].toCharArray();
//                    int num=0;
//                    for(int i=0;i<temp.length;i++){
//                        if(temp[i]=='.')
//                            num=i;
//                    }
//                    str[str.length-1].substring(0,num+1);
//                    result.add(str[str.length-1]);
//                    break;
                }
            }
            if(result.size()>=1) {
                String str = result.get(result.size() - 1);
                String resstr = "";
                while (true) {
                    int index = str.indexOf(".");
                    if(index==-1 || index==str.length()-1){
                        resstr+=str;
                        break;
                    }
                    char c1 = str.charAt(index + 1);
                    if (c1 <= 'Z' && c1 >= 'A') {
                        break;
                    } else {
//                        System.out.println(file.getName());
//                        System.out.println(index);
                        resstr += str.substring(0, index);

                        str = str.substring(index + 1);
                    }
                }
                if(resstr.contains("责任编辑")||resstr.contains("责任编校")){
//                    System.out.println(resstr);
                    int index=Math.max(resstr.indexOf("责任编辑"),resstr.indexOf("责任编校"));

                    resstr=resstr.substring(0,index-1);
//                    System.out.println(resstr);
                }
                result.remove(result.size() - 1);
                result.add(resstr);
            }

            return result;
        }
        return result;
    }

    @Override
    public String extractForeword(File file) throws IOException {
        return "";
    }
}
