
public class Intersection {
    public int column;
    public int line;
    private Color color;
    private Boolean isMarker;
    private Boolean isRing;

    Intersection(int column, int line) {
        this.column = column;
        this.line = line;
        this.isMarker = false;
        this.isRing = false;
    }

    Intersection(char column, int line) {
        this.column = (int) column - 65;
        this.line = line - 1;
        this.isMarker = false;
        this.isRing = false;
    }

    public void putRing(Color color) {
        this.isRing = true;
        this.color = color;
    }

    public void putMarker(Color color) {
        this.isMarker = true;
        this.color = color;
    }

    public void removeRing() {
        this.isRing = false;
        this.color = this.isMarker() ? this.color : Color.UNDEFINED;
    }

    public void removeMarker() {
        this.isMarker = false;
        this.color = this.getRing() ? this.color : Color.UNDEFINED;
    }

    public Boolean isMarker() {
        return isMarker;
    }

    public Boolean getRing() {
        return isRing;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


}
