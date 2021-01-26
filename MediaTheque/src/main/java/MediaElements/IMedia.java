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
        return  "'" + getName() + "', "
                + "'" + getType().toString() + "', "
                + "'" + getData() + "', "
                + "'" + attributes.get("Date Inserted") + "', "
                + "'" + attributes.get("Original Path") + "', "
                + attributes.get("File Size") + ", "
                + "'" + attributes.get("Original Extension") + "'";
    }
}
