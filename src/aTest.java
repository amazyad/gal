import junit.framework.TestCase;


public class aTest extends TestCase {

	public aTest ( String name ) { super ( name ); }
	public void test_current_color ()
	{
		Yinsh test = new Yinsh ();
		assertTrue (test . current_color () == Ring.WHITE || test . current_color () == Ring.BLACK);
	}
	public void test_put_ring_anneau_existe (){
		Yinsh test = new Yinsh();
		char alpha='B';
		int ligne=1;
		Ring ring = test.current_color();
		try {
			test.put_ring(alpha, ligne, ring);
			
			assertTrue(test.anneau_existe(alpha, ligne, ring) == true);
		} catch (YinshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void test_put_ring_nombre_anneau(){
		Yinsh test = new Yinsh();
		int blackRing=test.nombre_anneau(Ring.BLACK);
		int whiteRing=test.nombre_anneau(Ring.WHITE);
		assertTrue((blackRing + whiteRing)==0);
	}
	
	public void test_is_initialized() throws YinshException{
		Yinsh test = new Yinsh();
		test.put_ring('B', 1, Ring.BLACK);
		
		test.put_ring('C', 1, Ring.WHITE);
		test.put_ring('B', 2, Ring.BLACK);
		test.put_ring('C', 2, Ring.WHITE);
		test.put_ring('B', 3, Ring.BLACK);
		test.put_ring('C', 3, Ring.WHITE);
		test.put_ring('B', 4, Ring.BLACK);
		test.put_ring('C', 4, Ring.WHITE);
		test.put_ring('B', 5, Ring.BLACK);
		test.put_ring('C', 5, Ring.WHITE);
		int nombre_anneau_white=test.nombre_anneau(Ring.WHITE);
		int nombre_anneau_black=test.nombre_anneau(Ring.BLACK);
		System.out.println("nombre de blanc"+nombre_anneau_white);
		System.out.println("nombre de blac"+nombre_anneau_black);
		assertTrue(test.is_initialized()==true);
		
	}
}
