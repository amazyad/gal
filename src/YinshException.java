
public class YinshException  extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ring ring;
	public YinshException(Ring ring){
		this.ring = ring;
	}
	
	public Ring getRing(){
		return ring;
	}
}
