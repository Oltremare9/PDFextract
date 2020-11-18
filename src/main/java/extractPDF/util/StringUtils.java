package extractPDF.util;

public class StringUtils {
    public static boolean isBlank(String str) {
        if (null == str) {
            return true;
        }
        for (Character c : str.toCharArray()) {
            if(!c.equals(' ')){
                return false;
            }
        }
        return true;
    }
}
