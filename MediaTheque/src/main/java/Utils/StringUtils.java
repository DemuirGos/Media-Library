package Utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StringUtils {
    public static String encode(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String decode(String str) {
        return new String(Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8)));
    }
}
