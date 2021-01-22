package MediaElements;
import java.util.*;

public class IMedia {
    private String name;
    private String type;
    private String raw;
    
    IMedia(String name, String type, String raw){
        this.raw = raw;
        this.type = type;
        this.name = name;
    }
    
    String getName() {
        return name;
    }
    
    String getType() {
        return type;
    }

    String getData() {
        return raw;
    }
}
