package extractPDF.Magazines;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LiaodongCollege extends AllMagazines {
    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String> list= super.extractQuatation(file);
        if(list.size()>0){
            String str=list.get(list.size()-1);
            int index=str.indexOf("责任编辑");
            if(index!=-1){
                str=str.substring(0,index-1);
                list.remove(list.size()-1);
                list.add(str);
            }

        }
        return list;
    }
}
