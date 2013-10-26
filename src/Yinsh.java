import java.util.ArrayList;
import java.util.List;

class Yinsh {

    private final int m_maxLigne = 11;
    private final int m_maxColonne = 11;


    private int m_nMarkers = 51;

    private Player m_blackPlayer;
    private Player m_whitePlayer;
    private Player m_turn;
    private Player m_winner;

    private boolean m_end;

    private Mode m_mode;

    //le plateau du jeu
    public Intersection[][] m_board = new Intersection[m_maxColonne][m_maxLigne];

    //pour gerer les cases inexistants en jeu
    //                             0  1  2  3   4   5   6   7   8   9   10
    //                             A  B  C  D   E   F   G   H   I   J   K
    private final int[] m_borderMin = new int[]{1, 0, 0, 0, 0, 1, 1, 2, 3, 4, 6};
    private final int[] m_borderMax = new int[]{4, 6, 7, 8, 9, 9, 10, 10, 10, 10, 9};

    public Yinsh() {
        m_end = false;
        for (int j = 0; j < m_maxColonne; j++)
            for (int i = 0; i < m_maxLigne; i++) {
                m_board[j][i] = initIntersection(j, i);
            }
        m_blackPlayer = new Player(Color.BLACK);
        m_whitePlayer = new Player(Color.WHITE);
        setFirstTurn();
    }

    private Intersection initIntersection(int column, int line) {
        Intersection point = new Intersection(column, line);
        Color color = Color.UNDEFINED;
        if (column > this.m_borderMax[line] || column < this.m_borderMin[line])
            color = Color.IMPOSSIBLE;
        point.setColor(color);
        return point;
    }


    public Player getBlackPlayer() {
        return m_blackPlayer;
    }


    public Player getWhitePlayer() {
        return m_whitePlayer;
    }


    public Player getTurn() {
        return m_turn;
    }

    public void setTurn(Player turn) {
        this.m_turn = turn;
    }

    public void changeTurn() {
        if (getTurn() == m_blackPlayer) setTurn(m_whitePlayer);
        else setTurn(m_blackPlayer);
    }

    public Player getWinner() {
        return m_winner;
    }

    public void setWinner(Player winner) {
        this.m_winner = winner;
    }

    public Mode getMode() {
        return m_mode;
    }

    public void setMode(Mode mode) {
        this.m_mode = mode;
    }

    public int getnMarkers() {
        return m_nMarkers;
    }

    public void setnMarkers(int nMarkers) {
        this.m_nMarkers = nMarkers;
    }

    public boolean isEnd() {
        return m_end;
    }

    public void setEnd() {
        m_end = true;
    }

    public Intersection getIntersection(char column, int line){
        int col = toY(column), lin = line - 1;
        return m_board[col][lin];
    }

    public Color currentColor() {
        int random = (int) (Math.random() * 2);
        Color color;
        if (random == 1)
            color = Color.BLACK;
        else
            color = Color.WHITE;

        return color;
    }

    public void setFirstTurn() {
        Color color = currentColor();
        if (color == Color.BLACK) setTurn(getBlackPlayer());
        else setTurn(getWhitePlayer());
    }

    public void putRing(Intersection point, Color color) throws YinshException {
        if (point.getColor() == Color.IMPOSSIBLE) throw new YinshException(TypeException.IMPOSSIBLE);
        if (point.isRing()) throw new YinshException(TypeException.RING_ALREADY_EXIST);
        point.putRing(color);
    }

    public int getRingNumber(Color color) {
        int counterRing = 0;
        for (int j = 0; j < m_maxColonne; j++) {
            for (int i = 0; i < m_maxLigne; i++) {
                if (m_board[j][i].getColor() == color) counterRing++;
            }
        }
        return counterRing;
    }

    public boolean isInitialized() {
        int blackRingNumber = getRingNumber(Color.BLACK);
        int whiteRingNumber = getRingNumber(Color.WHITE);
        return (blackRingNumber == 5 && whiteRingNumber == 5);
    }

    public void putMarker(Intersection point, Color color) throws YinshException {
        int c = point.m_column, l = point.m_line;
        if (!m_board[c][l].isRing()) throw new YinshException(TypeException.NEED_A_RING);
        else if (m_board[c][l].getColor() != color) throw new YinshException(TypeException.NOT_YOUR_TURN);
        setnMarkers(getnMarkers() - 1);
        m_board[c][l].putMarker(color);
    }

    private int[] getHowToMoveFromToIntersection(Intersection point1, Intersection point2) {
        int[] howToMoveFromToIntersection = new int[2];
        if (point2.m_column - point1.m_column != 0)
            howToMoveFromToIntersection[0] = (point2.m_column - point1.m_column) / Math.abs(point2.m_column - point1.m_column);
        if (point2.m_line - point1.m_line != 0)
            howToMoveFromToIntersection[1] = (point2.m_line - point1.m_line) / Math.abs(point2.m_line - point1.m_line);
        return howToMoveFromToIntersection;
    }

    private boolean isPossibleMove(Intersection point1, Intersection point2) {
        int[] howToMove = getHowToMoveFromToIntersection(point1, point2);
        boolean isPossibleMove = (point2.getColor() == Color.UNDEFINED);
        boolean doIJumpedOverAMarker = false;
        int i = point1.m_column + howToMove[0], j = point1.m_line + howToMove[1];
        while ((i != point2.m_column || j != point2.m_line) && isPossibleMove) {
            if (m_board[i][j].isRing()) isPossibleMove = false;
            if (m_board[i][j].isMarker()) doIJumpedOverAMarker = true;
            if (m_board[i][j].getColor() == Color.UNDEFINED && doIJumpedOverAMarker) isPossibleMove = false;
            i += howToMove[0];
            j += howToMove[1];
        }
        return isPossibleMove;
    }

    public void moveRing(Intersection point1, Intersection point2) throws YinshException {
        int cFrom = point1.m_column, cTo = point2.m_column, lFrom = point1.m_line, lTo = point2.m_line;

        if (m_board[cTo][lTo].getColor() != Color.UNDEFINED) throw new YinshException(TypeException.MUST_BE_EMPTY);
        if (!isPossibleMove(m_board[cFrom][lFrom], m_board[cTo][lTo]))
            throw new YinshException(TypeException.CANT_JUMP_OVER_RING);

        flipMarkers(m_board[cFrom][lFrom], m_board[cTo][lTo]);

        m_board[cTo][lTo].putRing(m_board[cFrom][lFrom].getColor());
        m_board[cFrom][lFrom].removeRing();
    }

    public void flipMarkers(Intersection point1, Intersection point2) {
        int[] howToMove = getHowToMoveFromToIntersection(point1, point2);
        int i = point1.m_column + howToMove[0], j = point1.m_line + howToMove[1];

        while (i != point2.m_column || j != point2.m_line) {

            if (m_board[i][j].isMarker())
                if (m_board[i][j].getColor() == Color.BLACK) m_board[i][j].setColor(Color.WHITE);
                else m_board[i][j].setColor(Color.BLACK);

            i += howToMove[0];
            j += howToMove[1];
        }
    }

    private void removeMarker(Intersection point) {
        point.removeMarker();
    }

    private Boolean isRow(Intersection point1, Intersection point2) {
        int[] howToMove = getHowToMoveFromToIntersection(point1, point2);
        int i = point1.m_column + howToMove[0], j = point1.m_line + howToMove[1];
        Color color = point1.getColor();
        Boolean isARow = point1.isMarker();

        while (!(i == point2.m_column && j == point2.m_line) && isARow) {
            if (!(m_board[i][j].isMarker() && m_board[i][j].getColor() == color))
                isARow = false;
            i += howToMove[0];
            j += howToMove[1];
        }
        return isARow;
    }

    public void removeRow(Intersection point1, Intersection point2) {
        int[] howToMove = getHowToMoveFromToIntersection(point1, point2);
        int i = point1.m_column, j = point1.m_line;
        if (isRow(point1, point2)) {
            while (i != point2.m_column || j != point2.m_line) {
                removeMarker(m_board[i][j]);
                i += howToMove[0];
                j += howToMove[1];
            }
            removeMarker(m_board[i][j]);
        }
    }

    public void removeRing(Intersection point) {
        m_turn.setScore(m_turn.getScore() + 1);
        point.removeRing();
    }

    public Intersection nextIntersection(Intersection point, int[] direction) {
        Intersection rPoint = m_board[0][0];
        if (point.m_column < 10 && point.m_column > 0 && point.m_line < 10 && point.m_line > 0)
            rPoint = m_board[point.m_column + direction[0]][point.m_line + direction[1]];
        return rPoint;
    }

    public List<Intersection> getPossibleMove(Intersection point) {
        List<Intersection> possibleMove = new ArrayList<Intersection>();
        int[][] direction = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}};
        if (point.isRing()) {
            for (int[] aDirection : direction) {
                Intersection iterationPoint = nextIntersection(point, aDirection);
                while (iterationPoint.getColor() == Color.UNDEFINED || iterationPoint.isMarker()) {
                    if (isPossibleMove(point, iterationPoint)) possibleMove.add(iterationPoint);
                    iterationPoint = nextIntersection(iterationPoint, aDirection);
                }
            }
        }
        return possibleMove;
    }

    public List<Intersection> getMarkers() {
        List<Intersection> markers = new ArrayList<Intersection>();
        for (int i = 0; i < m_maxColonne - 4; i++) {
            for (int j = this.m_borderMin[i]; j <= this.m_borderMax[j] - 4; j++) {
                if (m_board[i][j].isMarker() && m_board[i][j].getColor() == m_turn.getColor())
                    markers.add(m_board[i][j]);
            }
        }
        return markers;
    }

    public List<Intersection[]> getRows() {
        List<Intersection[]> rows = new ArrayList<Intersection[]>();
        List<Intersection> markers = getMarkers();
        int[][] rDirection = {{1, 0}, {0, 1}, {1, 1}};
        for (Intersection marker : markers) {
            for (int[] aRDirection : rDirection) {
                if (isRow(marker, m_board[marker.m_column + (4 * aRDirection[0])][marker.m_line + (4 * aRDirection[1])]))
                    rows.add(new Intersection[]{marker, m_board[marker.m_column + (4 * aRDirection[0])][marker.m_line + (4 * aRDirection[1])]});
            }
        }
        return rows;
    }

    private void endGameWhoHastheHighestScoreIsTheWinner() {
        setEnd();
        if (m_blackPlayer.getScore() > m_whitePlayer.getScore()) setWinner(getBlackPlayer());
        else if (m_whitePlayer.getScore() > m_blackPlayer.getScore()) setWinner(getWhitePlayer());
    }

    private void whoGetThisScoreIsTheWinner(int score) {
        if (m_blackPlayer.getScore() == score) {
            setEnd();
            setWinner(getBlackPlayer());
        } else if (m_whitePlayer.getScore() == score) {
            setEnd();
            setWinner(getWhitePlayer());
        }
    }

    public void tryEndTheMatch() {
        if (getnMarkers() <= 0)
            endGameWhoHastheHighestScoreIsTheWinner();
        else {
            switch (getMode()) {
                case NORMAL:
                    whoGetThisScoreIsTheWinner(3);
                    break;
                case BLITZ:
                    whoGetThisScoreIsTheWinner(1);
                    break;
            }
        }
    }




    public enum Mode {BLITZ, NORMAL}

    public int toY(char colonne) {
        int ascii = (int) colonne;
        return ascii - 65;
    }


    public int toX(int ligne) {
        return ligne - 1;
    }


}