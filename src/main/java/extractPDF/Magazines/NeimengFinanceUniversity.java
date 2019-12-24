package extractPDF.Magazines;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NeimengFinanceUniversity extends  AllMagazines {
    @Override
    public String extractKeyword(File file) throws IOException {
        String str=super.extractKeyword(file);
        if(str.length()>1){
            str=str.substring(1);
        }
        return str;
    }

    @Override
    public String extractAbstract(File file) throws IOException {
        String str=super.extractAbstract(file);
        if(str.length()>1){
            str=str.substring(1);
        }
        return str;
    }

    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String>list= super.extractQuatation(file);
        if(list.size()>0){
            String str=list.get(list.size()-1);
            int index=str.indexOf("责任");
            if(index!=-1){
                str=str.substring(0,index-1);
            }
            if(str.length()>80){
                list.remove(list.size()-1);
                return list;
            }
            list.remove(list.size()-1);
            list.add(str);
        }
        return list;
    }
}
