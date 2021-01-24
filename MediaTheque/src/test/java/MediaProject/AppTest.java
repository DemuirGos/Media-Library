package MediaProject;

import Utils.StringUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */

    @Test
    void testIMedia() {

    }

    @Test
    void testEncodingDecoding() {
        int n = new Random().nextInt(1000000);
        while (n-- > 0) {
            byte[] array = new byte[7];
            new Random().nextBytes(array);
            String str = new String(array, StandardCharsets.UTF_8);
            assertEquals(str, StringUtils.decode(StringUtils.encode(str)));
        }
    }
}
