package extractPDF.Magazines;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JournalofTianjinUniversityofFinanceandEconomics extends AllMagazines{
    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String> list=super.removeExtraAuthor(super.extractQuatation(file));
        if(list.size()>1){
            String str=list.get(list.size()-1);
            int index=str.lastIndexOf(".");
            if(index!=-1&&index<list.size()){
                char c=str.charAt(index);
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
}
