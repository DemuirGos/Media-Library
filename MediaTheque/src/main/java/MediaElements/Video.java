package MediaElements;

public class Video extends IMedia{
    Video () {}

    Video (MediaType type) {
        super(type);
    }

    Video(String name, MediaType type,String data){
        super(name,type,data);
    }
}
