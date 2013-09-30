
public enum Color {
	NONE("none", false, false),
	IMPOSSIBLE("impossible", false, false),
	BLACKRING("black", false, true),
	WHITERING("white", false, true),
	BLACKMARK("black", true, false),
	WHITEMARK("white", true, false),
	WHITERINGMARKED("white", true, true),
	BLACKRINGMARKED("black" , true, true);
	
	private final String color;
	private final boolean marked;
	private final boolean ring;
	Color(String color, boolean marked, boolean ring){
		this.color = color;
		this.marked = marked;
		this.ring = ring;
	}
	
	
	
	/**
	 * @return the is_marked
	 */
	public boolean Is_marked() {
		return marked;
	}

	/**
	 * @return the is_ring
	 */
	public boolean Is_ring() {
		return ring;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	
	public boolean Is_white(){
		return (getColor() == "white") ? true : false;
	}
	
	public boolean Is_black(){
		return !(Is_white());
	}
}
