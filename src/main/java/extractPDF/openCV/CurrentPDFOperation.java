package extractPDF.openCV;

import extractPDF.util.FileOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

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
import static extractPDF.util.FileOperation.deleteFiles;

public class CurrentPDFOperation {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    static final String pngOutPath="D:\\LDA\\result_output\\pngOutPath\\";

    /**
    * @Description: 输入pdf以及对应的参数 （对于pdf本身的） 画出线并存储在pngOutPath中
    * @Param: [file, rect]
    * @return: void
    * @Author: whn
    * @Date: 2020/2/11
    */
    public static void showCurrentLine(File file,Rectangle rect){
        String output=pngOutPath+
                file.getName().substring(0,file.getName().length()-4);
        PDDocument pdDocument= null;
        try {
            pdDocument = PDDocument.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PDPage pdPage = pdDocument.getPage(0);
        PDFRenderer renderer = new PDFRenderer(pdDocument);
        for(int i=0;i<pdDocument.getNumberOfPages();i++){
            float currentWidth=pdPage.getMediaBox().getWidth();
            float currentHeight=pdPage.getMediaBox().getHeight();
            BufferedImage image = null;
            try {
                image = renderer.renderImage(i, 3f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int imageWidth=image.getWidth();
            int imageHeight=image.getHeight();
            Graphics g = image.getGraphics();
            float r= currentWidth/imageWidth;
            g.setColor(Color.RED);//画笔颜色
            g.drawRect((int)(rect.x/r),(int)(rect.y/r),(int)(rect.width/r),(int)(rect.height/r));
            FileOutputStream out = null;//输出图片的地址
            try {
                out = new FileOutputStream(output+i+".png");
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
    //中文pdf另存路径
    static final String tempPDFPath="D:\\LDA\\runnning_output\\tempPDF\\temp.pdf";
    //切分临时图片位置
    static final String tempSplitPath="D:\\LDA\\runnning_output\\tempSplitPng";
    //拆分图片dpi
    static final int dpi=250;
    
    public static List<Integer> getPageRectangle(File pdfFile) {
        File source= new File(pdfFile.getAbsolutePath());
        File des=new File(tempPDFPath);
        try {
            FileOperation.copyFileUsingStream(source,des);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        deleteFiles(tempSplitPath);
        pdf2Image(tempPDFPath,tempSplitPath,dpi);
        Mat image = Imgcodecs.imread(tempSplitPath+"\\temp_1.png");
        List<Integer> list= new ArrayList<>();
        list.add(0,image.width());
        list.add(1,image.height());

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
        list.add(2,top);
        list.add(3,bottom);

//        Mat testImage = new Mat(visualImage, Imgproc.boundingRect(contours.get(1)));
        showImg(visualImage);
        return list;
    }

    /**
    * @Description: 将图片坐标 转换为对应的pdf坐标
    * @Param: [pdfFile, currentPos]
    * @return: java.awt.Rectangle
    * @Author: whn
    * @Date: 2020/2/11
    */
    public static Rectangle transferToRect(File pdfFile,List<Integer> currentPos) throws IOException {
        PDDocument document=PDDocument.load(pdfFile);
        PDPage page=document.getPage(0);
        //目标坐标
        float width=page.getMediaBox().getWidth();
        float height=page.getMediaBox().getHeight();
        //当前坐标
        int curWidth=currentPos.get(0);
        int curHeight=currentPos.get(1);
        int top=currentPos.get(2);
        int bottom=currentPos.get(3);
        //计算当前坐标形成的rect
        int x=0;
        int y=top;
        int w=curWidth;
        int h=bottom-top;
        //计算比例
        float ratio=width/curWidth;
        //形成rect
        Rectangle rect=new Rectangle((int)(x*ratio),(int)(y*ratio),(int)(w*ratio),(int)(h*ratio));
        return rect;
    }
    
    public static void  main(String args[]) throws IOException {
        //获取切割之后的图片坐标
        File file=new File("D:\\LDA\\金融论文\\贵州财经大学学报\\贵州财经大学学报2012\\" +
                "财政转型_以_有形之手_促进科学发展_武力.pdf");
        List list=getPageRectangle(file);

        //转换为pdf内坐标

            Rectangle rect=transferToRect(file,list);

        
//
        showCurrentLine(file,rect);
    }
}
