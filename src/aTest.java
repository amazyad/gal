import junit.framework.TestCase;


public class aTest extends TestCase {
	public aTest ( String name ) { super ( name ); }
	public void test_current_color ()
	{
		Yinsh color = new Yinsh ();
		assertTrue (color . current_color () == Color.WHITE || color . current_color () == Color.BLACK);
	}
	public void test_put_ring (){
		//assertTrue();
		//put_ring ();
	}
}
