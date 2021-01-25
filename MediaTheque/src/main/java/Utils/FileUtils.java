package Utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

public class FileUtils {
    public static String readFile(File f) {
        StringBuilder strBld = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            int str;
            while ((str = br.read()) != -1) {
                strBld.append((char)str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strBld.toString();
    }

    public static Dictionary<String,String> getAttributes(File d) {
        var attributes = new Hashtable<String,String>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        attributes.put("Date Inserted", formatter.format(new Date()));
        attributes.put("Original Path", d.getPath());
        attributes.put("File Size", String.valueOf(d.length()));
        return attributes;
    }
}