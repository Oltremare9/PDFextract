package extractPDF.openCV;

import extractPDF.Config;
import extractPDF.util.FileOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.io.*;
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

    //indexOutOfBounce
    private static int errorCount=0;
    public static String pdf2Image(File pdfFile, String dstImgFolder, int dpi) throws IOException {


        File file = pdfFile;
        PDDocument pdDocument;
        String res = "";
        String imagePDFName = "temp"; // 获取图片文件名

        pdDocument = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(pdDocument);
        /* dpi越大转换后越清晰，相对转换速度越慢 */
        StringBuffer imgFilePath = null;
//        if (pdDocument.getNumberOfPages() > 1) {
//            for (int i = 0; i < 2; i++) {
        for (int i = 0; i < pdDocument.getNumberOfPages(); i++) {
            System.out.println("正在转换第" + i + "页");
            res = Config.getPngTempPath(file);
            String imgFilePathPrefix = res + imagePDFName;
            imgFilePath = new StringBuffer();
            imgFilePath.append(imgFilePathPrefix);
            imgFilePath.append("_");
            imgFilePath.append(i);
            imgFilePath.append(".png");
            File dstFile = new File(res);
            if (!dstFile.exists()) {
                dstFile.mkdirs();
                dstFile.createNewFile();
            }
            BufferedImage image = null;
            try {
                image = renderer.renderImageWithDPI(i, dpi);
            } catch (IndexOutOfBoundsException e) {
                FileOperation.copyIndexErrorFile(pdfFile);
                image=renderer.renderImageWithDPI(0, dpi);
            }


            ImageIOUtil.writeImage(image, res + "temp_" + i + ".png", dpi);
            System.out.println("第" + i + "页转换完成");
            System.out.println("PDF文档转PNG图片成功！");
//                return imgFilePath.toString();
        }
//        }
        pdDocument.close();

        return res;

    }

    public static Mat inputStream2Mat(String path) throws IOException {
        InputStream inputStream = new FileInputStream(new File(path));
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = bis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        os.close();
        bis.close();
        inputStream.close();

        Mat encoded = new Mat(1, os.size(), 0);
        encoded.put(0, 0, os.toByteArray());

        Mat decoded = Imgcodecs.imdecode(encoded, -1);
        encoded.release();
        return decoded;
    }


}
