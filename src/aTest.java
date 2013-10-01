import junit.framework.TestCase;


public class aTest extends TestCase {

	public aTest ( String name ) { super ( name ); }
	public void test_current_color ()
	{
		Yinsh test = new Yinsh ();
		Color color = test . current_color ();
		assertTrue (color == Color.WHITERING || color == Color.BLACKRING);
	}
	public void test_put_ring_anneau_existe (){
		Yinsh test = new Yinsh();
		test.current_color();
		int alpha=test.toY('B');
		int ligne=test.toX(1);
		Color color = test.getTurn();
		try {
			test.put_ring(alpha, ligne, color);
			assertTrue(test.anneau_existe(alpha, ligne, color) == true);
		} catch (YinshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void test_put_ring_nombre_anneau(){
		Yinsh test = new Yinsh();
		int blackRing=test.nombre_anneau(Color.BLACKRING);
		int whiteRing=test.nombre_anneau(Color.WHITERING);
		assertTrue((blackRing + whiteRing)==0);
	}
	
	public void test_is_initialized() throws YinshException{
		Yinsh test = new Yinsh();
		test.setTurn(Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(1), Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('D'), test.toX(2), Color.BLACKRING);
		test.put_ring(test.toY('C'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('F'), test.toX(7), Color.BLACKRING);
		test.put_ring(test.toY('D'), test.toX(6), Color.WHITERING);
		test.put_ring(test.toY('G'), test.toX(9), Color.BLACKRING);
		test.put_ring(test.toY('G'), test.toX(8), Color.WHITERING);
		test.put_ring(test.toY('J'), test.toX(8), Color.BLACKRING);
		test.put_ring(test.toY('H'), test.toX(6), Color.WHITERING);
		assertTrue(test.is_initialized()==true);
		
	}
	
	public void test_put_marker() throws YinshException{
		Yinsh test = new Yinsh();
		test.setTurn(Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(1), Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('D'), test.toX(2), Color.BLACKRING);
		test.put_ring(test.toY('C'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('F'), test.toX(7), Color.BLACKRING);
		test.put_ring(test.toY('D'), test.toX(6), Color.WHITERING);
		test.put_ring(test.toY('G'), test.toX(9), Color.BLACKRING);
		test.put_ring(test.toY('G'), test.toX(8), Color.WHITERING);
		test.put_ring(test.toY('J'), test.toX(8), Color.BLACKRING);
		test.put_ring(test.toY('H'), test.toX(6), Color.WHITERING);
		test.put_marker(test.toY('D'), test.toX(2), Color.BLACKMARK);
		assertTrue(test.anneau_existe(test.toY('D'), test.toX(2), Color.BLACKRINGMARKED)==true);
	}
	
	public void test_move_ring() throws YinshException{
		Yinsh test = new Yinsh();
		test.setTurn(Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(1), Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('D'), test.toX(2), Color.BLACKRING);
		test.put_ring(test.toY('C'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('F'), test.toX(7), Color.BLACKRING);
		test.put_ring(test.toY('D'), test.toX(6), Color.WHITERING);
		test.put_ring(test.toY('G'), test.toX(9), Color.BLACKRING);
		test.put_ring(test.toY('G'), test.toX(8), Color.WHITERING);
		test.put_ring(test.toY('J'), test.toX(8), Color.BLACKRING);
		test.put_ring(test.toY('H'), test.toX(6), Color.WHITERING);
		test.put_marker(test.toY('D'), test.toX(2), Color.BLACKMARK);
		test.move_ring(test.toY('D'), test.toX(2), test.toY('D'), test.toX(5));
		assertTrue(test.anneau_existe(test.toY('D'), test.toX(5), Color.BLACKRING)==true);
	}
	
	public void test_put_marker_1() throws YinshException{
		Yinsh test = new Yinsh();
		test.setTurn(Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(1), Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('D'), test.toX(2), Color.BLACKRING);
		test.put_ring(test.toY('C'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('F'), test.toX(7), Color.BLACKRING);
		test.put_ring(test.toY('D'), test.toX(6), Color.WHITERING);
		test.put_ring(test.toY('G'), test.toX(9), Color.BLACKRING);
		test.put_ring(test.toY('G'), test.toX(8), Color.WHITERING);
		test.put_ring(test.toY('J'), test.toX(8), Color.BLACKRING);
		test.put_ring(test.toY('H'), test.toX(6), Color.WHITERING);
		test.put_marker(test.toY('D'), test.toX(2), Color.BLACKMARK);
		test.move_ring(test.toY('D'), test.toX(2), test.toY('D'), test.toX(5));
		try {
			test.put_marker(test.toY('D'), test.toX(3), Color.BLACKMARK);
			assertTrue(false);
		} catch (YinshException e) {
			assertTrue(true);
		}
		
	}
	
	public void test_put_marker_2() throws YinshException{
		Yinsh test = new Yinsh();
		test.setTurn(Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(1), Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('D'), test.toX(2), Color.BLACKRING);
		test.put_ring(test.toY('C'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('F'), test.toX(7), Color.BLACKRING);
		test.put_ring(test.toY('D'), test.toX(6), Color.WHITERING);
		test.put_ring(test.toY('G'), test.toX(9), Color.BLACKRING);
		test.put_ring(test.toY('G'), test.toX(8), Color.WHITERING);
		test.put_ring(test.toY('J'), test.toX(8), Color.BLACKRING);
		test.put_ring(test.toY('H'), test.toX(6), Color.WHITERING);
		test.put_marker(test.toY('D'), test.toX(2), Color.BLACKMARK);
		
	}
	
	public void test_put_marker_3() throws YinshException{
		Yinsh test = new Yinsh();
		test.setTurn(Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(1), Color.BLACKRING);
		test.put_ring(test.toY('B'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('D'), test.toX(2), Color.BLACKRING);
		test.put_ring(test.toY('C'), test.toX(2), Color.WHITERING);
		test.put_ring(test.toY('F'), test.toX(7), Color.BLACKRING);
		test.put_ring(test.toY('D'), test.toX(6), Color.WHITERING);
		test.put_ring(test.toY('G'), test.toX(9), Color.BLACKRING);
		test.put_ring(test.toY('G'), test.toX(8), Color.WHITERING);
		test.put_ring(test.toY('J'), test.toX(8), Color.BLACKRING);
		test.put_ring(test.toY('H'), test.toX(6), Color.WHITERING);
		test.put_marker(test.toY('D'), test.toX(2), Color.BLACKMARK);
		try {
			test.move_ring(test.toY('D'), test.toX(2), test.toY('I'), test.toX(7));
			assertTrue(false);
		} catch (YinshException e) {
			assertTrue(true);
		}
	}
	
	public void test_move_ring_ex6() throws YinshException{
		Yinsh test = new Yinsh();
		test.plateau[4][3] = Color.BLACKRINGMARKED;
		test.plateau[4][4] = Color.BLACKMARK;
		test.plateau[4][5] = Color.WHITEMARK;
		test.plateau[4][6] = Color.WHITEMARK;
		test.plateau[4][7] = Color.BLACKMARK;
		test.plateau[4][8] = Color.WHITEMARK;
		test.plateau[4][9] = Color.NONE;
		test.setTurn(Color.BLACKRING);
		test.move_ring(test.toY('E'), test.toX(4), test.toY('E'), test.toX(10));
		test.flipMarkers(test.toY('E'), test.toX(4), test.toY('E'), test.toX(10));
		assertTrue(test.anneau_existe(test.toY('E'), test.toX(4), Color.BLACKMARK)
				&& test.anneau_existe(test.toY('E'), test.toX(6), Color.BLACKMARK)
				&& test.anneau_existe(test.toY('E'), test.toX(7), Color.BLACKMARK)
				&& test.anneau_existe(test.toY('E'), test.toX(9), Color.BLACKMARK)
				&& test.anneau_existe(test.toY('E'), test.toX(5), Color.WHITEMARK)
				&& test.anneau_existe(test.toY('E'), test.toX(8), Color.WHITEMARK));
	}
	
	public void test_remove_row(){
		Yinsh test = new Yinsh();
		test.plateau[4][5] = Color.BLACKMARK;
		test.plateau[5][6] = Color.BLACKMARK;
		test.plateau[6][7] = Color.BLACKMARK;
		test.plateau[7][8] = Color.BLACKMARK;
		test.plateau[8][9] = Color.BLACKMARK;
		test.setTurn(Color.BLACKRING);
		test.printPlateau();
		System.out.println(test.is_row(test.toY('E'), test.toX(6), test.toY('I'), test.toX(10)));
		test.remove_row(test.toY('E'), test.toX(6), test.toY('I'), test.toX(10));
		test.printPlateau();
		assertTrue(test.anneau_existe(test.toY('E'), test.toX(6), Color.NONE)
				&& test.anneau_existe(test.toY('F'), test.toX(7), Color.NONE)
				&& test.anneau_existe(test.toY('G'), test.toX(8), Color.NONE)
				&& test.anneau_existe(test.toY('H'), test.toX(9), Color.NONE)
				&& test.anneau_existe(test.toY('I'), test.toX(10), Color.NONE));
	}
	
	public void test_remove_ring(){
		Yinsh test = new Yinsh();
		test.plateau[7][9] = Color.BLACKRING;
		test.remove_ring(7, 9);
		assertTrue(test.anneau_existe(test.toY('E'), test.toX(6), Color.NONE)
				&& test.anneau_existe(test.toY('F'), test.toX(7), Color.NONE)
				&& test.anneau_existe(test.toY('G'), test.toX(8), Color.NONE)
				&& test.anneau_existe(test.toY('H'), test.toX(9), Color.NONE)
				&& test.anneau_existe(test.toY('I'), test.toX(10), Color.NONE)
				&& test.anneau_existe(test.toY('H'), test.toX(10), Color.NONE)
				&& test.BlackPlayer == 1);
	}
	
	public void test_add_player_a_point(){
		Yinsh test = new Yinsh();
		test.plateau[7][9] = Color.BLACKRING;
		test.remove_ring(7, 9);
		assertTrue(test.BlackPlayer == 1);
	}
	
	public void test_get_possible_move(){
		Yinsh test = new Yinsh();
		test.plateau[0][4] = Color.BLACKRING;
		test.plateau[1][3] = Color.WHITERING;
		test.plateau[1][4] = Color.BLACKMARK;
		test.plateau[1][5] = Color.WHITERING;
		test.plateau[2][3] = Color.BLACKMARK;
		test.plateau[2][4] = Color.WHITEMARK;
		test.plateau[2][5] = Color.WHITEMARK;
		test.plateau[3][1] = Color.WHITERING;
		test.plateau[3][2] = Color.BLACKRING;
		test.plateau[3][5] = Color.WHITEMARK;
		test.plateau[3][6] = Color.BLACKMARK;
		test.plateau[4][2] = Color.WHITEMARK;
		test.plateau[4][3] = Color.BLACKRING;
		test.plateau[4][4] = Color.BLACKMARK;
		test.plateau[4][5] = Color.WHITEMARK;
		test.plateau[4][6] = Color.WHITEMARK;
		test.plateau[4][7] = Color.BLACKMARK;
		test.plateau[4][8] = Color.WHITEMARK;
		test.plateau[5][7] = Color.WHITERING;
		test.plateau[6][6] = Color.WHITERING;
		test.plateau[6][7] = Color.BLACKRING;
		test.plateau[6][8] = Color.WHITEMARK;
		test.plateau[7][6] = Color.WHITEMARK;
		test.plateau[7][7] = Color.BLACKRING;
		test.plateau[8][7] = Color.BLACKMARK;
		//test.printPlateau();
		int[][] possible_move = test.get_possible_move(4 , 3);
		for(int i = 0; i < possible_move.length; i++){
			System.out.println("("+test.toY(possible_move[i][0])+","+(possible_move[i][1] + 1)+")");
		}
	}
	
	public void test_is_possible_move(){
		Yinsh test = new Yinsh();
		test.plateau[0][4] = Color.BLACKRING;
		test.plateau[1][3] = Color.WHITERING;
		test.plateau[1][4] = Color.BLACKMARK;
		test.plateau[1][5] = Color.WHITERING;
		test.plateau[2][3] = Color.BLACKMARK;
		test.plateau[2][4] = Color.WHITEMARK;
		test.plateau[2][5] = Color.WHITEMARK;
		test.plateau[3][1] = Color.WHITERING;
		test.plateau[3][2] = Color.BLACKRING;
		test.plateau[3][5] = Color.WHITEMARK;
		test.plateau[3][6] = Color.BLACKMARK;
		test.plateau[4][2] = Color.WHITEMARK;
		test.plateau[4][3] = Color.BLACKRING;
		test.plateau[4][4] = Color.BLACKMARK;
		test.plateau[4][5] = Color.WHITEMARK;
		test.plateau[4][6] = Color.WHITEMARK;
		test.plateau[4][7] = Color.BLACKMARK;
		test.plateau[4][8] = Color.WHITEMARK;
		test.plateau[5][7] = Color.WHITERING;
		test.plateau[6][6] = Color.WHITERING;
		test.plateau[6][7] = Color.BLACKRING;
		test.plateau[6][8] = Color.WHITEMARK;
		test.plateau[7][6] = Color.WHITEMARK;
		test.plateau[7][7] = Color.BLACKRING;
		test.plateau[8][7] = Color.BLACKMARK;
		int YF = 4;
		int XF = 3;
		int YT = 9;
		int XT = 8;
		test.printPlateau();
		boolean possible = test.is_possible_move(YF, XF, YT, XT);
		assertTrue(possible == true);
	}
	
	public void make_fig_5(Yinsh test){
		test.setMode(1);
		test.setTurn(Color.BLACKRING);
		test.plateau[2][3] = Color.WHITERING;
		test.plateau[3][1] = Color.BLACKMARK;
		test.plateau[3][3] = Color.WHITERING;
		test.plateau[3][4] = Color.WHITEMARK;
		test.plateau[3][5] = Color.BLACKRING;
		test.plateau[4][2] = Color.BLACKRING;
		test.plateau[4][3] = Color.BLACKRING;
		test.plateau[4][4] = Color.WHITEMARK;
		test.plateau[4][5] = Color.BLACKMARK;
		test.plateau[4][6] = Color.WHITEMARK;
		test.plateau[4][7] = Color.WHITEMARK;
		test.plateau[5][2] = Color.WHITERING;
		test.plateau[5][3] = Color.BLACKMARK;
		test.plateau[5][5] = Color.WHITERING;
		test.plateau[5][6] = Color.BLACKMARK;
		test.plateau[6][3] = Color.WHITEMARK;
		test.plateau[6][5] = Color.BLACKRING;
		test.plateau[6][6] = Color.WHITEMARK;
		test.plateau[6][7] = Color.BLACKMARK;
		test.plateau[7][7] = Color.BLACKMARK;
		test.plateau[7][8] = Color.BLACKMARK;
		test.plateau[7][9] = Color.BLACKRING;
		test.plateau[8][7] = Color.WHITERING;
		test.plateau[8][9] = Color.BLACKMARK;
		//test.plateau[9][10] = Color.BLACKMARK;
	}
	
	public void test_mode_blitz(){
		Yinsh test = new Yinsh();
		// blitz mode
		make_fig_5(test);
		test.printPlateau();
		test.remove_row(4, 5, 8, 9);
		test.remove_ring(7, 9);
		System.out.println("BlackPoint = "+test.BlackPlayer + " || "+"WhitePoint = "+test.WhitePlayer);
		assertTrue(test.winner == Color.BLACKRING);
	}
	//1.1.10 Dixième histoire
	/**
	 * @author amazyad
	 * @description  Dans certaines configurations, un alignement de plus de 5 marqueurs est réalisé. Dans ce cas, le joueur 
	 * doit indiquer quels sont les 5 marqueurs qui seront retirés du plateau.
	 */
	public void test_row(){
		Yinsh test = new Yinsh();
		make_fig_5(test);
		test.plateau[9][10] = Color.BLACKMARK;
		test.printPlateau();
		//test.remove_row(4, 5, 8, 9);
		//test.remove_ring(7, 9);
		test.printPlateau();
		System.out.println("BlackPoint = "+test.BlackPlayer + " || "+"WhitePoint = "+test.WhitePlayer);
		assertTrue(test.winner == Color.BLACKRING);
	}
	
}

