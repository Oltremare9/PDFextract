package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//中央财经大学学报
public class JournalofCentralUniversityofFinanceandEconomics extends AllMagazines{
    @Override
    public String extractKeyword(File file) throws IOException {
        String str= super.extractKeyword(file);
        if(str.length()>1) {
            str = str.substring(1);
            int index=str.indexOf("Key");
            if(index!=-1)
                str=str.substring(0,index);
        }

        return str;
    }

    @Override
    public String extractAbstract(File file) throws IOException {
        String str= super.extractAbstract(file);
        if(str.length()>1)
            str=str.substring(1);
        return str;
    }

    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String> list=super.extractQuatation(file);
        if(list.size()>0){
            String str=list.get(list.size()-1);
            int index=str.indexOf("责任");
            if(index!=-1){
                str=str.substring(0,index-1);
            }
            list.remove(list.size()-1);
            list.add(str);
        }
        if(list.size()>1){
            String str=list.get(list.size()-1);
            int index=str.lastIndexOf(".");
            if(index!=-1 && index<(str.length()-1)){
                char c=str.charAt(index+1);
                if(c>='A'&& c<='z')
                    str=str.substring(0,index);
            }
            list.remove(list.size()-1);
            if(str.length()<80){
                list.add(str);
            }
        }
        return list;
    }

    @Override
    public String extractForeword(File file) throws IOException {
        return super.extractForeword(file);
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
