import junit.framework.TestCase;


class aTest extends TestCase {
	public aTest ( String name ) { super ( name ); }
	public void test_current_color ()
	{
		Yinsh current_color = new Yinsh ();
		assertTrue (current_color . current_color () == "BLACK");
	}
}
