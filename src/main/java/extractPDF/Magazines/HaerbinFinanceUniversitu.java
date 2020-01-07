package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HaerbinFinanceUniversitu  extends AllMagazines{
    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String> list= super.extractQuatation(file);
        if(list.size()>0){
            if(list.get(list.size()-1).contains("责任")){
                int index=list.get(list.size()-1).lastIndexOf(".");
                if(index!=-1) {
                    String str = list.get(list.size() - 1);
                    list.remove(list.size() - 1);
                    list.add(str.substring(0, index));
                }
            }
        }
        return list;
    }

    @Override
    public String extractKeyword(File file) throws IOException {
        String res=super.extractKeyword(file);
        if(Integer.parseInt(file.getParent().substring(file.getParent().length()-4))<2012 && res.length()>1) {
            res=res.substring(1);
        }
        return  res;
    }

    @Override
    public String extractAbstract(File file) throws IOException {
        String res=super.extractAbstract(file);
        if(Integer.parseInt(file.getParent().substring(file.getParent().length()-4))<2012 && res.length()>1) {
            res=res.substring(1);
        }
        return  res;
    }


    @Override
    public String extractText(File file) throws IOException {
        String res = "";
        if (file.isFile()) {

            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                if(s.contains("Key")||s.contains("引言"))
                    break;
            }
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                if(s.contains("参考文献")||s.contains("责任编辑"))
                    break;
                else if (s.contains("表\\d")||s.contains("图\\d")||s.contains("作者简介")||
                        s.contains("DOI")) {
                    continue;
                }else{
                    res+=s;
                }

            }

        }
        return res;
    }
}
