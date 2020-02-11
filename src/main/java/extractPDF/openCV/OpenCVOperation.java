package extractPDF.openCV;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class OpenCVOperation {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void clearNoiseContours(List<MatOfPoint> contours, int area) {
        Iterator<MatOfPoint> iterator = contours.iterator();
        while (iterator.hasNext()) {
            MatOfPoint matOfPoint = iterator.next();
            MatOfPoint2f mat2f = new MatOfPoint2f();
            matOfPoint.convertTo(mat2f, CvType.CV_32FC1);
            RotatedRect rect = Imgproc.minAreaRect(mat2f);
            if (rect.boundingRect().area() < area) {
                iterator.remove();
            }
            ;
        }
    }

    public static void showImg(String path) {
        Mat image = Imgcodecs.imread(path);
        showImg(image);
    }

    public static void showImg(Mat mat) {
        ImageViewer imageViewer = new ImageViewer(mat, "hello");
        imageViewer.imshow();
    }

    /**
     * 模糊处理
     *
     * @param mat
     * @return
     */
    public static Mat blur(Mat mat) {
        Mat blur = new Mat();
        Imgproc.blur(mat, blur, new Size(5, 5));
        return blur;
    }

    /**
     * 膨胀
     *
     * @param mat
     * @return
     */
    public static Mat dilate(Mat mat, int size) {
        Mat dilate = new Mat();
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));
        //膨胀
        Imgproc.dilate(mat, dilate, element, new Point(-1, -1), 1);
        return dilate;
    }

    /**
     * 腐蚀
     *
     * @param mat
     * @return
     */
    public static Mat erode(Mat mat, int size) {
        Mat erode = new Mat();
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));
        //腐蚀
        Imgproc.erode(mat, erode, element, new Point(-1, -1), 1);
        return erode;
    }


    public static String pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
        File file = new File(PdfFilePath);
        PDDocument pdDocument;
        try {
            int dot = file.getName().lastIndexOf('.');
            String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
            String imgFolderPath = dstImgFolder;

            pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            /* dpi越大转换后越清晰，相对转换速度越慢 */
            StringBuffer imgFilePath = null;
            for (int i = 0; i < pdDocument.getNumberOfPages(); i++) {
//            if (pdDocument.getNumberOfPages() > 2) {
//                Integer i = 1;
                System.out.println("正在转换第" + i + "页");
                String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
                imgFilePath = new StringBuffer();
                imgFilePath.append(imgFilePathPrefix);
                imgFilePath.append("_");
                imgFilePath.append(i);
                imgFilePath.append(".png");
                File dstFile = new File(imgFilePath.toString());
                BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                ImageIO.write(image, "png", dstFile);
                System.out.println("第" + i + "页转换完成");
                System.out.println("PDF文档转PNG图片成功！");
//                return imgFilePath.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
