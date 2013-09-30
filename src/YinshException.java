
public class YinshException  extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public YinshException(){
		
	}
	
	
	
	public YinshException(Color color, TypeException type) {
		super(type.getMessage());
		// TODO Auto-generated constructor stub
	    }
}
