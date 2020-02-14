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


}
