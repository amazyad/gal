import junit.framework.TestCase;

import java.util.List;

public class aTest extends TestCase {
    public aTest(String name) {
        super(name);
    }

    public void testCurrentColor() throws Exception {
        Yinsh test = new Yinsh();
        Color color = test.currentColor();
        assertTrue(color == Color.BLACK || color == Color.WHITE);
    }

    public void testPutRingInitialState() {
        Yinsh test = new Yinsh();
        int blackRing = test.getRingNumber(Color.BLACK);
        int whiteRing = test.getRingNumber(Color.WHITE);
        assertTrue(blackRing + whiteRing == 0);
    }

    public void testPutRingAfterPutRing() throws YinshException {
        Yinsh test = new Yinsh();
        Intersection point = test.m_board[3][4];
        test.putRing(point, Color.WHITE);
        int blackRing = test.getRingNumber(Color.BLACK);
        int whiteRing = test.getRingNumber(Color.WHITE);
        assertTrue(blackRing + whiteRing == 1);
    }

    public void testPutRingImpossible() {
        Yinsh test = new Yinsh();
        Intersection point = test.m_board[0][0];
        try {
            test.putRing(point, Color.WHITE);
            assertTrue(false);
        } catch (YinshException e) {
            assertTrue(true);
        }
    }

    public void testPutRing() throws YinshException {
        Yinsh test = new Yinsh();
        Intersection point = test.m_board[3][4];
        test.putRing(point, Color.BLACK);
        assertTrue(test.m_board[3][4].isRing() && test.m_board[3][4].getColor() == Color.BLACK);
    }

    public void testPutRingAlreadyExist() {
        Yinsh test = new Yinsh();
        try {
            test.putRing(test.m_board[3][4], Color.BLACK);
            test.putRing(test.m_board[3][4], Color.BLACK);
            assertTrue(false);
        } catch (YinshException e) {
            assertTrue(true);
        }
    }

    public void testIsInitialized() throws YinshException {
        Yinsh test = new Yinsh();
        Intersection[] points = {test.m_board[3][4], test.m_board[3][5], test.m_board[3][6], test.m_board[3][7], test.m_board[3][8],
                test.m_board[4][2], test.m_board[4][3], test.m_board[4][4], test.m_board[4][5], test.m_board[4][6]};
        for (int i = 0; i < points.length; i += 2) {
            test.putRing(points[i], Color.BLACK);
            test.putRing(points[i + 1], Color.WHITE);
        }
        assertTrue(test.isInitialized());
    }

    void makeFig2(Yinsh test) throws YinshException {
        Intersection[] blackPoints = {test.m_board[1][0], test.m_board[3][1], test.m_board[5][6], test.m_board[7][7], test.m_board[8][7]};
        Intersection[] whitePoints = {test.m_board[1][1], test.m_board[2][1], test.m_board[3][5], test.m_board[5][7], test.m_board[7][5]};
        for (int i = 0; i < blackPoints.length; i++) {
            test.putRing(blackPoints[i], Color.BLACK);
            test.putRing(whitePoints[i], Color.WHITE);
        }
    }

    public void testPutMarker() throws YinshException {
        Yinsh test = new Yinsh();
        makeFig2(test);
        test.putMarker(test.getIntersection('D', 2), Color.BLACK);
        assertTrue(test.m_board[3][1].isMarker());
    }

    public void testMoveRing() throws YinshException {
        Yinsh test = new Yinsh();
        makeFig2(test);
        test.putMarker(test.getIntersection('D', 2), Color.BLACK);
        test.moveRing(test.getIntersection('D', 2), test.getIntersection('D', 5));
        assertTrue(test.m_board[test.toY('D')][test.toX(5)].isRing() &&
                !test.m_board[test.toY('D')][test.toX(2)].isRing());
    }

    public void testPutMarkerOnOtherPlayerRing() {
        Yinsh test = new Yinsh();
        try {
            makeFig2(test);
            test.putMarker(test.getIntersection('D', 2), Color.WHITE);
            assertTrue(false);
        } catch (YinshException e) {
            assertTrue(true);
        }

    }

    public void testPutMarkerOnEmptyField() {
        Yinsh test = new Yinsh();
        try {
            makeFig2(test);
            test.putMarker(test.getIntersection('D', 3), Color.BLACK);
            assertTrue(false);
        } catch (YinshException e) {
            assertTrue(true);
        }
    }

    public void testMoveRingTryJumpOverARing() {
        Yinsh test = new Yinsh();
        try {
            makeFig2(test);
            test.moveRing(test.getIntersection('D', 2), test.getIntersection('I', 7));
            assertTrue(false);
        } catch (YinshException e) {
            assertTrue(true);
        }
    }

    void makeFig3Rings(Yinsh test) {
        int[][] blackRings = {{0, 4}, {3, 2}, {4, 3}, {6, 7}, {7, 7}};
        int[][] whiteRings = {{1, 3}, {1, 5}, {3, 1}, {5, 7}, {6, 6}};
        for (int[] whiteMarker : whiteRings) test.m_board[whiteMarker[0]][whiteMarker[1]].putRing(Color.WHITE);
        for (int[] blackMarker : blackRings) test.m_board[blackMarker[0]][blackMarker[1]].putRing(Color.BLACK);
    }

    void makeFig3Markers(Yinsh test) {
        int[][] blackMarkers = {{1, 4}, {2, 3}, {3, 6}, {4, 4}, {4, 7}, {8, 7}};
        int[][] whiteMarkers = {{2, 4}, {2, 5}, {3, 5}, {4, 2}, {4, 5}, {4, 6}, {4, 8}, {6, 8}, {7, 6}};
        for (int[] whiteMarker : whiteMarkers) test.m_board[whiteMarker[0]][whiteMarker[1]].putMarker(Color.WHITE);
        for (int[] blackMarker : blackMarkers) test.m_board[blackMarker[0]][blackMarker[1]].putMarker(Color.BLACK);
    }

    public void testFlipMarkers() throws YinshException {
        Yinsh test = new Yinsh();
        makeFig3Rings(test);
        makeFig3Markers(test);
        test.m_board[test.toY('E')][test.toX(4)].putMarker(Color.BLACK);
        test.moveRing(test.getIntersection('E', 4), test.getIntersection('E', 10));
        assertTrue((test.m_board[test.toY('E')][test.toX(4)].getColor() == Color.BLACK) &&
                (test.m_board[test.toY('E')][test.toX(6)].getColor() == Color.BLACK) &&
                (test.m_board[test.toY('E')][test.toX(7)].getColor() == Color.BLACK) &&
                (test.m_board[test.toY('E')][test.toX(9)].getColor() == Color.BLACK) &&
                (test.m_board[test.toY('E')][test.toX(5)].getColor() == Color.WHITE) &&
                (test.m_board[test.toY('E')][test.toX(8)].getColor() == Color.WHITE));
    }

    void makeFig5JustTheRow(Yinsh test) {
        int[][] blackMarkers = {{4, 5}, {5, 6}, {6, 7}, {7, 8}, {8, 9}};
        for (int[] blackMarker : blackMarkers) {
            test.m_board[blackMarker[0]][blackMarker[1]].putMarker(Color.BLACK);
        }
        test.m_board[7][9].putRing(Color.BLACK);
    }

    public void testRemoveRow() {
        Yinsh test = new Yinsh();
        makeFig5JustTheRow(test);
        test.setTurn(test.getBlackPlayer());
        test.removeRow(test.m_board[4][5], test.m_board[8][9]);
        test.removeRing(test.m_board[7][9]);
        assertTrue(!test.m_board[4][5].isMarker() &&
                !test.m_board[5][6].isMarker() &&
                !test.m_board[6][7].isMarker() &&
                !test.m_board[7][8].isMarker() &&
                !test.m_board[8][9].isMarker() &&
                !test.m_board[7][9].isRing() &&
                test.getBlackPlayer().getScore() == 1);
    }

    public void testGetPossibleMove() {
        Yinsh test = new Yinsh();
        makeFig3Rings(test);
        makeFig3Markers(test);
        List<Intersection> points = test.getPossibleMove(test.m_board[4][3]);
        for (int i = 0; i < points.size(); i++)
            assertTrue(points.contains(test.m_board[test.toY('E')][test.toX(2)]) &&
                    points.contains(test.m_board[test.toY('E')][test.toX(10)]) &&
                    points.contains(test.m_board[test.toY('D')][test.toX(4)]) &&
                    points.contains(test.m_board[test.toY('F')][test.toX(4)]) &&
                    points.contains(test.m_board[test.toY('G')][test.toX(4)]) &&
                    points.contains(test.m_board[test.toY('H')][test.toX(4)]) &&
                    points.contains(test.m_board[test.toY('I')][test.toX(4)]) &&
                    points.contains(test.m_board[test.toY('F')][test.toX(5)]) &&
                    points.contains(test.m_board[test.toY('G')][test.toX(6)]) &&
                    points.contains(test.m_board[test.toY('J')][test.toX(9)]));

    }

    public void testBlitzGame() {
        Yinsh test = new Yinsh();
        test.setMode(Yinsh.Mode.BLITZ);
        makeFig5JustTheRow(test);
        test.setTurn(test.getBlackPlayer());
        test.removeRow(test.m_board[4][5], test.m_board[8][9]);
        test.removeRing(test.m_board[7][9]);
        test.tryEndTheMatch();
        assertTrue(test.getWinner() == test.getBlackPlayer() && test.isEnd());
    }


    void makeFig2DifferentColorRow(Yinsh test) {
        makeFig5JustTheRow(test);
        int[][] whiteMarkers = {{3, 5}, {3, 6}, {3, 7}, {3, 8}, {3, 9}};
        for (int[] whiteMarker : whiteMarkers) {
            test.m_board[whiteMarker[0]][whiteMarker[1]].putMarker(Color.WHITE);
        }
        test.m_board[2][3].putRing(Color.WHITE);
    }

    public void testRemoveRowFromMultipleRows() {
        Yinsh test = new Yinsh();
        makeFig5JustTheRow(test);
        test.setTurn(test.getBlackPlayer());
        List<Intersection[]> rows = test.getRows();
        test.removeRow(rows.get(1)[0], rows.get(1)[1]);
        test.removeRing(test.m_board[7][9]);
        assertTrue(!test.m_board[5][6].isMarker() &&
                !test.m_board[6][7].isMarker() &&
                !test.m_board[7][8].isMarker() &&
                !test.m_board[8][9].isMarker() &&
                !test.m_board[9][10].isMarker() &&
                !test.m_board[7][9].isRing() &&
                test.getBlackPlayer().getScore() == 1);
    }

    public void testDifferentRowColor() {
        Yinsh test = new Yinsh();
        makeFig2DifferentColorRow(test);
        test.setTurn(test.getBlackPlayer());
        List<Intersection[]> rows = test.getRows();
        test.removeRow(rows.get(0)[0], rows.get(0)[1]);
        test.removeRing(test.m_board[7][9]);
        test.changeTurn();
        rows = test.getRows();
        test.removeRow(rows.get(0)[0], rows.get(0)[1]);
        test.removeRing(test.m_board[2][3]);
        assertTrue(test.getBlackPlayer().getScore() == 1 && test.getWhitePlayer().getScore() == 1);
    }

    public void testNormalMode() {
        Yinsh test = new Yinsh();
        test.setMode(Yinsh.Mode.NORMAL);
        test.getBlackPlayer().setScore(3);
        test.tryEndTheMatch();
        assertTrue(test.isEnd() && test.getWinner() == test.getBlackPlayer());
    }

    public void testNullMatch() {
        Yinsh test = new Yinsh();
        test.setnMarkers(0);
        test.tryEndTheMatch();
        assertTrue(test.isEnd() && test.getWinner() == null);
    }

    public void testBlackWinnerWith2Points() {
        Yinsh test = new Yinsh();
        test.setMode(Yinsh.Mode.NORMAL);
        test.getBlackPlayer().setScore(2);
        test.setnMarkers(0);
        test.tryEndTheMatch();
        assertTrue(test.isEnd() && test.getWinner() == test.getBlackPlayer());
    }
}
