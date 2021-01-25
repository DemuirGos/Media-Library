package Utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public static Map<String,String> getAttributes(File d) {
        var attributes = new Hashtable<String,String>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        var name = d.getName(); 
        attributes.put("Original Extension", name.substring(name.lastIndexOf(".") + 1));
        attributes.put("Date Inserted", formatter.format(new Date()));
        attributes.put("Original Path", d.getPath());
        attributes.put("File Size", String.valueOf(d.length()));
        return attributes;
    }

    
    public static Map<String,String> getAttributes(int i) { // only for testing to be removed use getAttributes(File d) instead 
        var attributes = new Hashtable<String,String>();
        attributes.put("Original Extension", "mp4" + String.valueOf(i));
        attributes.put("Date Inserted", "01/01/000" + String.valueOf(i));
        attributes.put("Original Path", "/root/rooter/rooster/" + String.valueOf(i));
        attributes.put("File Size", String.valueOf(i) + "mb");
        return attributes;
    }
}