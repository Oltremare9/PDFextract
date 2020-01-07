package extractPDF.Magazines;

import com.google.common.base.CharMatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JiangxiFinanceUniversity extends AllMagazines {


    @Override
    public ArrayList<String> extractQuatation(File file) throws IOException {
        return super.extractQuatation(file);
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
            res+=s;
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                if(s.contains("参考文献")||s.contains("责任编辑"))
                    break;
                else if (s.contains("表\\d")||s.contains("图\\d")) {
                    continue;
                }else{
                    res+=s;
                }

            }

        }
        return res;
    }
}
