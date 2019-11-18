package extractPDF.Magazines;

import java.io.File;
import java.io.IOException;

public class NorthEastFinanceUniversity extends AllMagazines {

    @Override
    public String extractKeyword(File file) throws IOException {
        String key=super.extractKeyword(file);
        if(key.length()>1)
            return key.substring(1);
        else
            return key;
    }

    @Override
    public String extractAbstract(File file) throws IOException {
        String key=super.extractAbstract(file);
        if(key.length()>1)
            return key.substring(1);
        else
            return key;
    }
}
