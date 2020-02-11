package extractPDF.util;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOperation {
    public static void copyFileUsingStream(File source, File dest)
            throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    public static void deleteFiles(String path) {
        File file = new File(path);
        if (!file.isDirectory()) {
            file.delete();
        } else if (file.isDirectory()) {
            String[] filelist = file.list();
            //獲取新的二級路徑
            for (int j = 0; j < filelist.length; j++) {
                File filessFile = new File(path + "\\" + filelist[j]);
                if (!filessFile.isDirectory()) {
                    filessFile.delete();
                } else if (filessFile.isDirectory()) {
                    deleteFiles(path + "\\" + filelist[j]);
                }
            }
            file.delete();
        }
    }
    public static List<Byte> convertFileToByteList(String path) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(
                new File(path));
        byte[] data=new byte[fileInputStream.available()];
        fileInputStream.read(data);
        fileInputStream.close();
        List<Byte> list=new ArrayList<>();
        for(int i=0;i<data.length;i++)
            list.add(data[i]);
        return list;
    }

}
