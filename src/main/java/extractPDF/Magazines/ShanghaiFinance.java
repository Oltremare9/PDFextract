package extractPDF.Magazines;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShanghaiFinance extends AllMagazines{
    @Override
    public String extractKeyword(File file) throws IOException {
        return super.extractKeyword(file);
    }

    @Override
    public String extractAbstract(File file) throws IOException {
        return super.extractAbstract(file);
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
        return list;
    }
}
