package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HainanFinance extends AllMagazines {
    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String> list= super.extractQuatation(file);
//        if(list.size()>0){
//            String str=list.get(list.size()-1);
//            if(str.contains("■")){
//                int index=str.lastIndexOf("■");
//                if(index!=-1)
//                    str=str.substring(0,index);
//            }
//            if(str.contains("△")){
//                int index=str.lastIndexOf("△");
//                if(index!=-1)
//                    str=str.substring(0,index);
//            }
//            Pattern pattern = Pattern.compile("^[a-zA-Z]");
//            Matcher matcher = pattern.matcher(str);
//            if(matcher.find()){
//                int index=str.lastIndexOf(".");
//                if(index!=-1)
//                    str=str.substring(0,index);
//            }
//            list.remove(list.size()-1);
//            list.add(str);
//        }
//        for(int i=0;i<list.size();i++){
//            if(list.get(i).contains("参考文献")){
//                list.remove(i);
//            }
//        }
        if(list.size()>0)
            list.remove(list.size()-1);
        return list;
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
