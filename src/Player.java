public class Player {
    private int m_score;
    private Color m_color;

    Player(Color color) {
        this.m_score = 0;
        this.m_color = color;
    }

    public int getScore() {
        return m_score;
    }

    public void setScore(int m_score) {
        this.m_score = m_score;
    }

    public Color getColor() {
        return m_color;
    }
}
