package extractPDF.Magazines;

import java.io.File;
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
}
