package Utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class StringUtils {
    public static String encode(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String decode(String str) {
        return new String(Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8)));
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
        System.out.println(sr.toString());
        return sr.toString();
    }
}
