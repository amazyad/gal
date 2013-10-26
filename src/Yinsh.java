import java.util.ArrayList;
import java.util.List;

class Yinsh {

    private final int maxLigne = 11;
    private final int maxColonne = 11;


    private int nMarkers = 51;

    private Player blackPlayer;
    private Player whitePlayer;
    private Player turn;
    private Player winner;

    private boolean isEnd;

    private Mode mode;

    //le plateau du jeu
    public Intersection[][] board = new Intersection[maxColonne][maxLigne];

    //pour gerer les cases inexistants en jeu
    //                             0  1  2  3   4   5   6   7   8   9   10
    //                             A  B  C  D   E   F   G   H   I   J   K
    private final int[] BorderMin = new int[]{1, 0, 0, 0, 0, 1, 1, 2, 3, 4, 6};
    private final int[] BorderMax = new int[]{4, 6, 7, 8, 9, 9, 10, 10, 10, 10, 9};

    public Yinsh() {
        isEnd = false;
        for (int j = 0; j < maxColonne; j++)
            for (int i = 0; i < maxLigne; i++) {
                board[j][i] = initIntersection(j, i);
            }
        blackPlayer = new Player(Color.BLACK);
        whitePlayer = new Player(Color.WHITE);
        setFirstTurn();
    }

    private Intersection initIntersection(int column, int line) {
        Intersection point = new Intersection(column, line);
        Color color = Color.UNDEFINED;
        if (column > this.BorderMax[line] || column < this.BorderMin[line])
            color = Color.IMPOSSIBLE;
        point.setColor(color);
        return point;
    }


    public Player getBlackPlayer() {
        return blackPlayer;
    }


    public Player getWhitePlayer() {
        return whitePlayer;
    }


    public Player getTurn() {
        return turn;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }

    public void changeTurn() {
        if (getTurn() == blackPlayer) setTurn(whitePlayer);
        else setTurn(blackPlayer);
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public int getnMarkers() {
        return nMarkers;
    }

    public void setnMarkers(int nMarkers) {
        this.nMarkers = nMarkers;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd() {
        isEnd = true;
    }

    public Intersection getIntersection(char column, int line){
        int c = toY(column), l = line - 1;
        return board[c][l];
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
        if (point.getRing()) throw new YinshException(TypeException.RING_ALREADY_EXIST);
        point.putRing(color);
    }

    public int getRingNumber(Color color) {
        int counterRing = 0;
        for (int j = 0; j < maxColonne; j++) {
            for (int i = 0; i < maxLigne; i++) {
                if (board[j][i].getColor() == color) counterRing++;
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
        int c = point.column, l = point.line;
        if (!board[c][l].getRing()) throw new YinshException(TypeException.NEED_A_RING);
        else if (board[c][l].getColor() != color) throw new YinshException(TypeException.NOT_YOUR_TURN);
        setnMarkers(getnMarkers() - 1);
        board[c][l].putMarker(color);
    }

    private int[] getHowToMoveFromToIntersection(Intersection point1, Intersection point2) {
        int[] howToMoveFromToIntersection = new int[2];
        if (point2.column - point1.column != 0)
            howToMoveFromToIntersection[0] = (point2.column - point1.column) / Math.abs(point2.column - point1.column);
        if (point2.line - point1.line != 0)
            howToMoveFromToIntersection[1] = (point2.line - point1.line) / Math.abs(point2.line - point1.line);
        return howToMoveFromToIntersection;
    }

    private boolean isPossibleMove(Intersection point1, Intersection point2) {
        int[] howToMove = getHowToMoveFromToIntersection(point1, point2);
        boolean isPossibleMove = (point2.getColor() == Color.UNDEFINED);
        boolean doIJumpedOverAMarker = false;
        int i = point1.column + howToMove[0], j = point1.line + howToMove[1];
        while ((i != point2.column || j != point2.line) && isPossibleMove) {
            if (board[i][j].getRing()) isPossibleMove = false;
            if (board[i][j].isMarker()) doIJumpedOverAMarker = true;
            if (board[i][j].getColor() == Color.UNDEFINED && doIJumpedOverAMarker) isPossibleMove = false;
            i += howToMove[0];
            j += howToMove[1];
        }
        return isPossibleMove;
    }

    public void moveRing(Intersection point1, Intersection point2) throws YinshException {
        int cFrom = point1.column, cTo = point2.column, lFrom = point1.line, lTo = point2.line;

        if (board[cTo][lTo].getColor() != Color.UNDEFINED) throw new YinshException(TypeException.MUST_BE_EMPTY);
        if (!isPossibleMove(board[cFrom][lFrom], board[cTo][lTo]))
            throw new YinshException(TypeException.CANT_JUMP_OVER_RING);

        flipMarkers(board[cFrom][lFrom], board[cTo][lTo]);

        board[cTo][lTo].putRing(board[cFrom][lFrom].getColor());
        board[cFrom][lFrom].removeRing();
    }

    public void flipMarkers(Intersection point1, Intersection point2) {
        int[] howToMove = getHowToMoveFromToIntersection(point1, point2);
        int i = point1.column + howToMove[0], j = point1.line + howToMove[1];

        while (i != point2.column || j != point2.line) {

            if (board[i][j].isMarker())
                if (board[i][j].getColor() == Color.BLACK) board[i][j].setColor(Color.WHITE);
                else board[i][j].setColor(Color.BLACK);

            i += howToMove[0];
            j += howToMove[1];
        }
    }

    private void removeMarker(Intersection point) {
        point.removeMarker();
    }

    private Boolean isRow(Intersection point1, Intersection point2) {
        int[] howToMove = getHowToMoveFromToIntersection(point1, point2);
        int i = point1.column + howToMove[0], j = point1.line + howToMove[1];
        Color color = point1.getColor();
        Boolean isARow = point1.isMarker();

        while (!(i == point2.column && j == point2.line) && isARow) {
            if (!(board[i][j].isMarker() && board[i][j].getColor() == color))
                isARow = false;
            i += howToMove[0];
            j += howToMove[1];
        }
        return isARow;
    }

    public void removeRow(Intersection point1, Intersection point2) {
        int[] howToMove = getHowToMoveFromToIntersection(point1, point2);
        int i = point1.column, j = point1.line;
        if (isRow(point1, point2)) {
            while (i != point2.column || j != point2.line) {
                removeMarker(board[i][j]);
                i += howToMove[0];
                j += howToMove[1];
            }
            removeMarker(board[i][j]);
        }
    }

    public void removeRing(Intersection point) {
        turn.setScore(turn.getScore() + 1);
        point.removeRing();
    }

    public Intersection nextIntersection(Intersection point, int[] direction) {
        Intersection rPoint = board[0][0];
        if (point.column < 10 && point.column > 0 && point.line < 10 && point.line > 0)
            rPoint = board[point.column + direction[0]][point.line + direction[1]];
        return rPoint;
    }

    public List<Intersection> getPossibleMove(Intersection point) {
        List<Intersection> possibleMove = new ArrayList<Intersection>();
        int[][] direction = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}};
        if (point.getRing()) {
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
        for (int i = 0; i < maxColonne - 4; i++) {
            for (int j = this.BorderMin[i]; j <= this.BorderMax[j] - 4; j++) {
                if (board[i][j].isMarker() && board[i][j].getColor() == turn.getColor())
                    markers.add(board[i][j]);
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
                if (isRow(marker, board[marker.column + (4 * aRDirection[0])][marker.line + (4 * aRDirection[1])]))
                    rows.add(new Intersection[]{marker, board[marker.column + (4 * aRDirection[0])][marker.line + (4 * aRDirection[1])]});
            }
        }
        return rows;
    }

    private void endGameWhoHastheHighestScoreIsTheWinner() {
        setEnd();
        if (blackPlayer.getScore() > whitePlayer.getScore()) setWinner(getBlackPlayer());
        else if (whitePlayer.getScore() > blackPlayer.getScore()) setWinner(getWhitePlayer());
    }

    private void whoGetThisScoreIsTheWinner(int score) {
        if (blackPlayer.getScore() == score) {
            setEnd();
            setWinner(getBlackPlayer());
        } else if (whitePlayer.getScore() == score) {
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