
public class Intersection {
    public int m_column;
    public int m_line;
    private Color m_color;
    private Boolean m_marker;
    private Boolean m_ring;

    Intersection(int column, int line) {
        this.m_column = column;
        this.m_line = line;
        this.m_marker = false;
        this.m_ring = false;
    }

    Intersection(char column, int line) {
        this.m_column = (int) column - 65;
        this.m_line = line - 1;
        this.m_marker = false;
        this.m_ring = false;
    }

    public void putRing(Color color) {
        this.m_ring = true;
        this.m_color = color;
    }

    public void putMarker(Color color) {
        this.m_marker = true;
        this.m_color = color;
    }

    public void removeRing() {
        this.m_ring = false;
        this.m_color = this.isMarker() ? this.m_color : Color.UNDEFINED;
    }

    public void removeMarker() {
        this.m_marker = false;
        this.m_color = this.isRing() ? this.m_color : Color.UNDEFINED;
    }

    public Boolean isMarker() {
        return m_marker;
    }

    public Boolean isRing() {
        return m_ring;
    }

    public Color getColor() {
        return this.m_color;
    }

    public void setColor(Color color) {
        this.m_color = color;
    }


}
