package extractPDF.CSV;


import com.csvreader.CsvReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;

public class ReadCSV {
    private String filePath;


    public ReadCSV(String filePath){
        this.filePath=filePath;
    }

    public String[] matchCsv(String starttitle,String endtitle,String mag) throws IOException {
        String res[]=new String[2];
        CsvReader csvReader=new CsvReader(filePath,',', Charset.forName("UTF-8"));
        while(csvReader.readRecord()){
            String title=csvReader.get(0).replaceAll("—|”|“|《|》|（|）|\\(|\\)|-|\\+|·|、|&|/","");
            String author=csvReader.get(1);
            String magazine=csvReader.get(2);
            if(magazine.equals(mag)){
                if(title.contains(starttitle)&&title.contains(endtitle)){
                    res[0]=title;
                    res[1]=author;
                    return res;
                }
            }
        }
        System.out.println("为匹配到标题的文章是-----"+starttitle+" "+endtitle+" "+mag);
        return res;
    }
}
