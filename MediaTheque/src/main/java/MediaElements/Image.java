package MediaElements;

public class Image extends IMedia {
    Image () {}

    Image(MediaType type) {
        super(type);
    }

    Image(String name,MediaType type,String data){
        super(name, type, data);
    }
}
    
