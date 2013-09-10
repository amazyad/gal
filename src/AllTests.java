import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
  public static Test suite() {
    TestSuite suite = new TestSuite (" Mes tests " );
    suite . addTest ( new TestSuite ( aTest . class ));
    return suite ;
  }
  
  public static void main ( String args [])
  {
    junit . textui . TestRunner . run ( AllTests . suite ());
  }

}




