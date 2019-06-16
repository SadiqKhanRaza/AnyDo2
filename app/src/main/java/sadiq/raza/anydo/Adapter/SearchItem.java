package sadiq.raza.anydo.Adapter;

public class SearchItem {
    private int imageResource;
    private String text1;

    public SearchItem(int imageResource, String text1) {
        this.imageResource = imageResource;
        this.text1 = text1;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText1() {
        return text1;
    }
}