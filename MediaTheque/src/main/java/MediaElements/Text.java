package MediaElements;

public class Text extends IMedia{
    Text () {}

    Text (MediaType type) {
        super(type);
    }

    Text(String name, MediaType type,String data){
        super(name,type,data);
    }
}
    
