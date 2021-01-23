package MediaElements;

public class Test {
    public static void main(String[] args) {
        MediaType mt = MediaType.Text;

        IMedia imd = mt.getMedia();

        imd.setData("dsafdsa");
        imd.setName("adf");

        System.out.println(imd);
    }
}
