package MediaElements;

public enum MediaType {
    Audio(new Audio()),
    Text(new Text()),
    Image(new Image()),
    Video(new Video());

    private final IMedia media;

    MediaType (IMedia media) {
        this.media = media;
    } 

    public IMedia getMedia() {
        return media;
    }
}
