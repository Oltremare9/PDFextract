package extractPDF.Magazines;

import extractPDF.CSV.WriteCSV;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ExtractFromDifMagazines {
    public static void extract(AllMagazines magazine, File file, WriteCSV writeCSV){
        String title="";
        try {
            title = magazine.extractTitle(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String author="";
        try {
            author = magazine.extractAuthor(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String keyword="";
        try {
            keyword = magazine.extractKeyword(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String abs="";
        try {
            abs = magazine.extractAbstract(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String foreword="";
        try {
            foreword = magazine.extractForeword(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text="";
        try {
            text=magazine.extractText(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> quo=new ArrayList<String>();
        try {
            quo = magazine.extractQuatation(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeCSV.wirteTitle(title);
        writeCSV.writeAuthor(author);
        writeCSV.writeMag(file);
        writeCSV.writeAbstract(abs);
//        writeCSV.writeForeword(foreword);
        writeCSV.writeText(text);
        writeCSV.writeKeyword(keyword);
        writeCSV.writeQuotation(quo);
//        writeCSV.writeQuotationWithEnglish(quo);

    }
}
