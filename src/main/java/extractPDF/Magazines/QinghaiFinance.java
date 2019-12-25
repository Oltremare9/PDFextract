package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class QinghaiFinance extends AllMagazines {
    @Override
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

                    res = res.substring(4, res.length());
//                    System.out.println(res);
                    break;
                }
            }

        }
        return res;
    }

    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String>list= super.extractQuatation(file);
        if(list.size()>0){
            String str=list.get(list.size()-1);
            int index=str.indexOf("责任");
            int index2=str.indexOf("作者");
            if(index!=-1 && index2!=-1 )
                index= Math.min(index,index2);
            else if (index!=-1 || index2!=-1)
                index= Math.max(index,index2);
            if(index!=-1){
                str=str.substring(0,index);
            }
            list.remove(list.size()-1);
            list.add(str);
        }
        return list;
    }
}
