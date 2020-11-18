package extractPDF.ExtractPart;

import extractPDF.Config;
import extractPDF.extractOperation.SimplyToTxt;
import extractPDF.openCV.CurrentPDFOperation;
import extractPDF.util.FileOperation;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static extractPDF.openCV.OpenCVOperation.pdf2Image;

public class ExtractReferenceByOpenCV {
    public static void main(String args[]) throws IOException {
        ExtractReferenceByOpenCV referenceByOpenCV = new ExtractReferenceByOpenCV();
        referenceByOpenCV.testExtractReference(new File("D:\\LDA\\金融论文finish\\acl_download\\acl_download2021\\2020.acl-main.54.pdf"), 9);
    }

    public void testExtractReference(File file, int pageNum) throws IOException {
        boolean isError = false;
        System.out.println(file.getAbsolutePath());
        String outPath = Config.getPngPath(file);
        String configPath = Config.getConfigOutPath(file);
        String tempPath = Config.getPngTempPath(file);
        java.util.List<Rectangle> res = new ArrayList<>();
        PDDocument pdDocument = PDDocument.load(file);
        pdDocument.close();


        //无需使用openCV时注释

        File f0 = new File(tempPath + "temp_" + (pageNum - 1) + "dpi" + Config.dip + ".png");//(pageNum-1)

        //每个文章一个config 定位config位置
        File f1 = new File(outPath + "ConfigPage" + pageNum + "dpi" + Config.dip + ".txt");


        //每个每年的期刊一个config
//        File f1=new File(configPath+"Config.txt");
        //如果存在切割图片 无须切割
        pdf2Image(file, Config.tempSplitPath, Config.dip);
        //不存在图片且
        // 不存在配置文件 解析rectangle并写入
        //全文解写rect
        List list = TempCurrentPDFOperation.getPageRectangle(file, pageNum);
        //前两页解析rect
//            List list=CurrentPDFOperation.getFirstTwoPagesRect(file);
        res = TempCurrentPDFOperation.transferToRect(file, list);
        new File(outPath).mkdirs();
        f1.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(f1));
        writer.write(list.get(0).toString());
//            FileOperation.copyFirstFile(file);
        writer.flush();
        writer.close();

        TempCurrentPDFOperation.showCurrentLine(file, res, pageNum);

        //无需openCV时注释
    }
}
