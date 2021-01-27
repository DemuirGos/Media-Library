package MediaElements;

import java.util.*;

//Medias Schema : Name Type Data Date Path Size Extension

public class IMedia {
    private final String name;
    private final MediaType type;
    private final String raw;
    private final Map<String, String> attributes;

    public IMedia(String name, MediaType type, String raw, Map<String, String> dictionary){
        this.raw = raw;
        this.type = type;
        this.name = name;
        this.attributes = dictionary;
    }
    
    public String getName() {
        return name;
    }
    
    public Map<String, String>  getAttributes() {
        return attributes;
    }

    public MediaType getType() {
        return type;
    }

    public String getData() {
        return raw;
    }

    @Override
    public String toString(){
        return  "'" + getName().replaceAll("'", "''") + "', "
                + "'" + getType().toString() + "', "
                + "'" + getData() + "', "
                + "'" + attributes.get("Date Inserted") + "', "
                + "'" + attributes.get("Original Path").replaceAll("'", "''") + "', "
                + attributes.get("File Size") + ", "
                + "'" + attributes.get("Original Extension") + "'";
    }

    public static IMedia Init(){
        var attributes = new Hashtable<String,String>();
        attributes.put("Original Extension", "Empty");
        attributes.put("Date Inserted", "00/00/00 00h00m00s");
        attributes.put("Original Path", "Empty");
        attributes.put("File Size", "Empty");
        return new IMedia("Selected Item", MediaType.Error, "0x00000000",attributes);
    }
}
