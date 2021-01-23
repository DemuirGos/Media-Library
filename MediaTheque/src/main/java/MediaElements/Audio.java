package MediaElements;

public class Audio extends IMedia {
    Audio () {}

    Audio (MediaType type) {
        super(type);
    }

    Audio(String name,MediaType type,String data){
        super(name,type,data);
    }
}
