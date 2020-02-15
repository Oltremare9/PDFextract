package extractPDF.UI.ActionThread;

import extractPDF.openCV.OpenCVOperation;

public class ShowImageOnScreen implements Runnable{
    private String path="";
    public ShowImageOnScreen(String path){
        this.path=path;
    }
    @Override
    public void run() {
        OpenCVOperation.showImg(path);
    }
}
