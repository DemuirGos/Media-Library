package Utils;

import java.util.Base64;
import java.util.List;

public class StringUtils {
    public static String encode(byte[] byteData) {
        return new String(Base64.getEncoder().encode(byteData));
    }

    public static byte[] decode(String str) {
        return Base64.getDecoder().decode(str.getBytes());
    }

    public static String mkStringPath (List<String> l) {
        String os = System.getProperty("os.name").toLowerCase();
        String sep = null;

        if (os.contains("win"))
            sep = "\\";
        else if (os.contains("linux"))
            sep = "/";

        StringBuilder sr = new StringBuilder();
        String finalSep = sep;

        l.forEach(x -> {
            sr.append(x);
            sr.append(finalSep);
        });
        sr.delete(sr.length() - 1, sr.length());
        return sr.toString();
    }
}
