package Utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUtils {
    public static byte[] readFile(File f) {
        byte[] data = new byte[(int)f.length()];
        try (var fis =  new FileInputStream(f)) {
            fis.read(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void writeFile(File file, byte[] data) {
            System.out.println(file.getAbsolutePath());
            try(var fos = new FileOutputStream(file)){
                fos.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static Map<String,String> getAttributes(File d) {
        var attributes = new Hashtable<String,String>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        var name = d.getName(); 
        attributes.put("Original Extension", name.substring(name.lastIndexOf(".") + 1));
        attributes.put("Date Inserted", formatter.format(new Date()));
        attributes.put("Original Path", d.getPath());
        attributes.put("File Size", String.valueOf(d.length()/1024));
        return attributes;
    }
}