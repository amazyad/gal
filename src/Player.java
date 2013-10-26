public class Player {
    private int score;
    private Color color;

    Player(Color color) {
        this.score = 0;
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Color getColor() {
        return color;
    }
}
