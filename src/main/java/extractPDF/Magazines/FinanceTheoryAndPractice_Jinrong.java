package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FinanceTheoryAndPractice_Jinrong extends AllMagazines{


    @Override
    public String extractKeyword(File file) throws IOException {
        String res = "";
        if (file.isFile()) {
            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                if (s.contains("关键词")) {
                    return s.substring(4);
                }
            }
        }
        return res;
    }


    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        ArrayList<String> res=super.extractQuatation(file);
        int size=res.size();
        if(size>1) {
            String last = res.get(size - 1);
            if (last.contains("责任编辑")) {
                int index = last.lastIndexOf("(");
                last = last.substring(0, index);
                res.remove(size - 1);
                res.add(last);
            }
        }
        return res;
    }
}
