package extractPDF.Magazines;

import java.io.File;
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
}
