package extractPDF.CSV;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteCSV {
    private int error = 0;
    private File csv = null;
    private WritableWorkbook workbook = null;
    private WritableSheet sheet = null;
    private int count;

    public WriteCSV(String out) {
        count = 0;
        this.csv = new File(out);
        if (csv.exists()) {
            csv.delete();
        }
        try {
            csv.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建文件失败");
        }
        try {
            workbook = Workbook.createWorkbook(csv);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建工作簿失败");
        }
        sheet = workbook.createSheet("sheet1", 0);
    }

    public void wirteTitle(String title) {
        Label label = new Label(0, count, title);
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
            System.out.println("添加标题失败");
        }
    }

    public void writeAuthor(String author) {
        Label label = new Label(1, count, author);
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
            System.out.println("添加作者失败");
        }
    }

    public void writeMag(File file) {
        String parentName = file.getParent();
        int index = parentName.lastIndexOf("\\");
        parentName = parentName.substring(index + 1);

        Label label = new Label(2, count, parentName);
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
            System.out.println("添加杂志信息失败");
        }
    }

    public void writeAbstract(String abs) {
        Label label = new Label(3, count, abs);
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
            System.out.println("添加摘要失败");
        }
    }

    public void writeKeyword(String keyword) {
        Label label = new Label(4, count, keyword);
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
            System.out.println("添加关键词失败");
        }
    }

    public void writeText(String text) {
        Label label = new Label(5, count, text);
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
            System.out.println("添加正文失败");
        }
    }

    public void writeForeword(String foreword) {
        Label label = new Label(5, count, foreword);
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
            System.out.println("添加前言失败");
        }
    }


    public void writeQuotation(ArrayList<String> quo) {
        int index = 0;
        for (int i = 0; i < quo.size(); i++) {


            //去除引文中英文文献
            String res = quo.get(i);
            Pattern pattern = Pattern.compile("^[a-zA-Z]");
            Matcher matcher = pattern.matcher(res);

            if (!matcher.find() && !res.startsWith(".")) {


                Label label = new Label(6 + index++, count, quo.get(i));

                try {
                    sheet.addCell(label);
                } catch (WriteException e) {
                    e.printStackTrace();
                    System.out.println("添加引文失败");
                }

            } else {
                error++;
            }

        }

//        System.out.println("删除英文文献" + error);
        count++;

    }

    public void writeQuotationWithEnglish(ArrayList<String> quo) {
        int index = 0;
        for (int i = 0; i < quo.size(); i++) {

            String res = quo.get(i);


            Label label = new Label(6 + index++, count, res);
            try {
                sheet.addCell(label);
            } catch (WriteException e) {
                e.printStackTrace();
                System.out.println("添加引文失败");
            }
        }

        count++;


    }

    public void write() {
        try {
            workbook.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
