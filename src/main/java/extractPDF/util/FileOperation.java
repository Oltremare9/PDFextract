package extractPDF.util;


import extractPDF.config;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOperation {
    public static void copyFile(File file)
            throws IOException {
        FileUtils.copyFile(file,new File(config.getFinishFilePath(file)));
    }

    public static void copyErrorFile(File file) throws IOException {
        FileUtils.copyFile(file,new File(config.getErrorFilePath(file)+file.getName()));
    }

    public static void copyErrorConfig(File file) throws IOException {
        FileUtils.copyFile(file,new File(config.getErrorConfigPath(file)+file.getName()));
    }

    public static void copyIndexErrorFile(File file) throws IOException {
        FileUtils.copyFile(file,new File(config.getErrorIndexPath(file)+file.getName()));
    }

    public static void copyFirstFile(File file) throws IOException{
        FileUtils.copyFile(file,new File(config.getFirstPdfPath(file)+"first.pdf"));
    }

}
