package extractPDF.openCV;

import extractPDF.config;
import extractPDF.util.FileOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static extractPDF.openCV.OpenCVOperation.*;

public class CurrentPDFOperation {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    static final String pngOutPath = config.pngOutPath;
//    static final String pngOutPath = "D:\\LDA\\result_output\\pngOutPath\\";


    /**
     * @Description: 输入pdf以及对应的参数 （对于pdf本身的） 画出线并存储在pngOutPath中
     * @Param: [file, rect]
     * @return: void
     * @Author: whn
     * @Date: 2020/2/11
     */
    public static void showCurrentLine(File file, List<Rectangle> list) throws IOException {
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
        String res = pngOutPath + mag + "\\" + magYear + "\\" + filename.substring(0, filename.length() - 4)
                + "\\";
        File dstFile = new File(res);
        if (!dstFile.exists()) {
            dstFile.mkdirs();
            dstFile.createNewFile();
        }
        String config=res+"conf.txt";

        //处理pdf绘制图片
        PDDocument pdDocument = null;
        try {
            pdDocument = PDDocument.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PDPage pdPage = pdDocument.getPage(0);
        PDFRenderer renderer = new PDFRenderer(pdDocument);
        for (int i = 0; i < pdDocument.getNumberOfPages(); i++) {
            float currentWidth = pdPage.getMediaBox().getWidth();
            float currentHeight = pdPage.getMediaBox().getHeight();
            BufferedImage image = null;
            try {
                image = renderer.renderImage(i, 3f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            Graphics g = image.getGraphics();
            float r = currentWidth / imageWidth;
            g.setColor(Color.RED);//画笔颜色
            g.drawRect((int) (list.get(i).x / r), (int) (list.get(i).y / r),
                    (int) (list.get(i).width / r), (int) (list.get(i).height / r));


            FileOutputStream out = null;//输出图片的地址
            try {
                out = new FileOutputStream(res + i + ".png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                ImageIO.write(image, "png", out);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * @Description: 输入pdf路径 返回上下切线对于图片的位置
     * @Param: [path]
     * @return: java.util.List<java.lang.Integer>
     * @Author: whn
     * @Date: 2020/2/11
     */
    //切分临时图片位置
//    static final String tempSplitPath = "D:\\LDA\\runnning_output\\tempSplitPng\\";//本地
    static final String tempSplitPath = config.tempSplitPath;
    //拆分图片dpi
    static final int dpi = config.dip;

    public static List<Rectangle> getPageRectangle(File pdfFile) throws IOException {
        List<Rectangle> list = new ArrayList<>();
//        deleteFiles(tempSplitPath);
        String returnPath = config.getPngTempPath(pdfFile);
        PDDocument pdDocument = PDDocument.load(pdfFile);
        Rectangle rec = new Rectangle();
        Mat image = inputStream2Mat(returnPath + "temp_0.png");
        rec.setSize(image.width(), image.height());
        list.add(0, rec);
        for (int j = 0; j < pdDocument.getNumberOfPages(); j++) {
            image = inputStream2Mat(returnPath + "temp_"+j+".png");
//        Mat image = Imgcodecs.imread(returnPath+"temp_1.png");
            //总体页面信息rec

            System.out.println(image.width() + "  " + image.height());
            Mat visualImage = image.clone();
            Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
            //二值
            double[] data = image.get(0, 0);
            int thres = (int) (data[0] - 5);
            Imgproc.threshold(image, image, thres, 255, Imgproc.THRESH_BINARY);
            //反色
            Core.bitwise_not(image, image);
            image = dilate(image, 30);
            //showImg(image);
            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            System.out.println(contours.size());
            clearNoiseContours(contours, 50000);
            int top = image.height();
            int bottom = 0;
            for (int i = 0; i < contours.size(); i++) {
                Rect rect = Imgproc.boundingRect(contours.get(i));
                if (rect.height > 160) {
                    Imgproc.rectangle(visualImage, rect.tl(), rect.br(), new Scalar(0, 0, 255), 3);
                    System.out.println("x:" + rect.x + "y:" + rect.y + "width:" + rect.width + "height" + rect.height);
                    if (rect.y + rect.height > bottom)
                        bottom = rect.y + rect.height;
                    if (rect.y < top)
                        top = rect.y;
                }
            }
            Rectangle rectangle = new Rectangle(0, top, rec.width, bottom - top);
            list.add(j + 1, rectangle);
        }

//        Mat testImage = new Mat(visualImage, Imgproc.boundingRect(contours.get(1)));
//        showImg(visualImage);
        return list;
    }

    /**
     * @Description: 将图片坐标 转换为对应的pdf坐标
     * @Param: [pdfFile, currentPos]
     * @return: java.awt.Rectangle
     * @Author: whn
     * @Date: 2020/2/11
     */
    public static List<Rectangle> transferToRect(File pdfFile, List<Rectangle> currentPos) throws IOException {
        List<Rectangle> res=new ArrayList<>();
        PDDocument document = PDDocument.load(pdfFile);
        PDPage page = document.getPage(0);
        //目标坐标
        float width = page.getMediaBox().getWidth();
        float height = page.getMediaBox().getHeight();
        for (int j = 1; j < currentPos.size(); j++) {

            //当前坐标
            int curWidth = currentPos.get(0).width;
            int curHeight = currentPos.get(0).height;

            //计算当前坐标形成的rect
            int x = currentPos.get(j).x;
            int y = currentPos.get(j).y;
            int w = currentPos.get(j).width;
            int h = currentPos.get(j).height;
            //计算比例
            float ratio = width / curWidth;
            //形成rect
            Rectangle rect = new Rectangle((int) (x * ratio), (int) (y * ratio), (int) (w * ratio), (int) (h * ratio));
            res.add(j-1,rect);
        }
        return res;
    }

//    public static void main(String args[]) throws IOException {
//        //获取切割之后的图片坐标
//        File file = new File("D:\\LDA\\金融论文\\贵州财经大学学报\\贵州财经大学学报2012\\" +
//                "财政转型_以_有形之手_促进科学发展_武力.pdf");
//        List list = getPageRectangle(file);
//
//        //转换为pdf内坐标
//
//        Rectangle rect = transferToRect(file, list);
//
//
////
//        showCurrentLine(file, rect);
//    }
}
