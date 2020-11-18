package extractPDF.UI.ActionThread;

import extractPDF.util.Pair;

import javax.swing.*;

import java.io.IOException;

import static extractPDF.extractOperation.exeFile.readFile;

public class PDF2TXT implements Runnable {
    private String pdfURL;
    private String txtURL;
    private Pair pair;
    public PDF2TXT(String pdfURL, String txtURL, Pair pair) {
        this.pdfURL = pdfURL;
        this.txtURL = txtURL;
        this.pair=pair;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            readFile(pdfURL, txtURL,pair);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("start time:" + start +
                "; end time:" + end +
                "; Run Time:" + (end - start) / 1000 + "(s)   "
                + (end - start) / 60000 + "（mins）"
                + ((end - start) / 1000) % 60 + "s");
        JOptionPane.showMessageDialog(null, "处理完成" +
                        "start time:" + start +
                        "; end time:" + end +
                        "; Run Time:" + (end - start) / 1000 + "(s)   "
                        + (end - start) / 60000 + "（mins）"
                        + ((end - start) / 1000) % 60 + "s",
                "完成", JOptionPane.INFORMATION_MESSAGE);
    }
}
