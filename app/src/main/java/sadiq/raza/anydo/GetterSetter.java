package sadiq.raza.anydo;

public class GetterSetter {

    String text;
    int icon;

    public GetterSetter() {
    }

    public GetterSetter(String text, int icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
