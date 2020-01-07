package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FinanceAndEconomics extends AllMagazines {


    @Override
    public String extractKeyword(File file) throws IOException {
        int length=super.extractKeyword(file).length();
        if(length>0)
            return super.extractKeyword(file).substring(1);
        else
            return super.extractKeyword(file);
    }

    @Override
    public String extractAbstract(File file) throws IOException {

        return "";
    }

    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String> arr=super.extractQuatation(file);
        ArrayList<String> res=new ArrayList<>();
        for(int i=0;i<arr.size();i++){
            if(!arr.get(i).contains("上接"))
                res.add(arr.get(i));
            else{
                int index=arr.get(i).lastIndexOf("上接");
                if(index>0) {
                    String str = arr.get(i).substring(0, index-1);
                    res.add(str.replaceAll("!",""));
                    break;
                }else{
                    continue;
                }
            }
        }
        return res;
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
                        ||s.contains("基金")||s.contains("收稿"))  {
                    continue;
                }else{
                    res+=s;
                }

            }

        }
        return res;
    }
}
