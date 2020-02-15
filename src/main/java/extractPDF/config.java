package extractPDF;

import java.io.File;

public class config {
    //展示图片框图片位置
    public static final String showLinePNGpath="D:\\";
    //结果图片输出位置
    public static final String pngOutPath = "F:\\LDA\\result_output\\pngOutPath\\";
    //图片拆分位置
    public static final String tempSplitPath = "F:\\LDA\\runnning_output\\tempSplitPng\\";
    //图片处理dpi
    public static final int dip=240;
    //处理完成文件剪切位置
    public static final String finishFilePath="D:\\LDA\\金融论文finish\\";
    //原本匹配参数csv文件位置
    public static final String rulePath="D:\\extract.csv";
    //错误文件输出位置
    public static final String errorPath="F:\\LDA\\error\\";

    public static String getErrorFilePath(File file){
        //原文件绝对目录
        String absPath = file.getAbsolutePath();
        //原文件文件名
        String filename = file.getName();
        //上一级目录名 期刊名+年
        String magYear;
        int index = absPath.lastIndexOf("\\");
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        magYear = absPath.substring(index + 1);
        //期刊名
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        String mag = absPath.substring(index + 1);
        String pngOutPath= config.errorPath;
        String res = pngOutPath + mag + "\\" + magYear + "\\" + filename.substring(0, filename.length() - 4)
                + "\\";
        return res;
    }
    public static String getErrorConfigPath(File file){
        //原文件绝对目录
        String absPath = file.getAbsolutePath();
        //原文件文件名 config.txt
        String filename = file.getName();
        //上一级目录名 pdf文件名
        String magYear;
        int index = absPath.lastIndexOf("\\");
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        magYear = absPath.substring(index + 1);
        //期刊名年月
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        String mag = absPath.substring(index + 1);

        String pngOutPath= config.errorPath;
        String res = pngOutPath +mag.substring(0,mag.length()-4)+"\\"+ mag + "\\" + magYear + "\\" ;
        return res;
    }
    public static String getPngPath(File file){
        //原文件绝对目录
        String absPath = file.getAbsolutePath();
        //原文件文件名
        String filename = file.getName();
        //上一级目录名 期刊名+年
        String magYear;
        int index = absPath.lastIndexOf("\\");
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        magYear = absPath.substring(index + 1);
        //期刊名
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        String mag = absPath.substring(index + 1);
        String pngOutPath= config.pngOutPath;
        String res = pngOutPath + mag + "\\" + magYear + "\\" + filename.substring(0, filename.length() - 4)
                + "\\";
        return res;
    }
    public static String getPngTempPath(File file){
        //原文件绝对目录
        String absPath = file.getAbsolutePath();
        //原文件文件名
        String filename = file.getName();
        //上一级目录名 期刊名+年
        String magYear;
        int index = absPath.lastIndexOf("\\");
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        magYear = absPath.substring(index + 1);
        //期刊名
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        String mag = absPath.substring(index + 1);
        String pngTempPath= config.tempSplitPath;
        String res = pngTempPath + mag + "\\" + magYear + "\\" + filename.substring(0, filename.length() - 4)
                + "\\";
        return res;
    }
    public static String getFinishFilePath(File file){
        //原文件绝对目录
        String absPath = file.getAbsolutePath();
        //原文件文件名
        String filename = file.getName();
        //上一级目录名 期刊名+年
        String magYear;
        int index = absPath.lastIndexOf("\\");
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        magYear = absPath.substring(index + 1);
        //期刊名
        absPath = absPath.substring(0, index);
        index = absPath.lastIndexOf("\\");
        String mag = absPath.substring(index + 1);
        String res = finishFilePath + mag + "\\" + magYear + "\\" + filename;
        return res;
    }
}
