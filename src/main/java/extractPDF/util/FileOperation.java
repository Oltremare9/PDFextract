package extractPDF.util;


import extractPDF.Config;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class FileOperation {
    public static void copyFile(File file)
            throws IOException {
        FileUtils.copyFile(file,new File(Config.getFinishFilePath(file)));
    }

    public static void copyErrorFile(File file) throws IOException {
        FileUtils.copyFile(file,new File(Config.getErrorFilePath(file)+file.getName()));
    }

    public static void copyErrorConfig(File file) throws IOException {
        FileUtils.copyFile(file,new File(Config.getErrorConfigPath(file)+file.getName()));
    }

    public static void copyIndexErrorFile(File file) throws IOException {
        FileUtils.copyFile(file,new File(Config.getErrorIndexPath(file)+file.getName()));
    }

    public static void copyFirstFile(File file) throws IOException{
        FileUtils.copyFile(file,new File(Config.getFirstPdfPath(file)+"first.pdf"));
    }

}
