package Utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StringUtils {
    public static String encode(byte[] byteData) {
        return new String(Base64.getEncoder().encode(byteData));
    }

    public static byte[] decode(String str) {
        return Base64.getDecoder().decode(str.getBytes());
    }
}
