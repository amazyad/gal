
public enum TypeException {
	STEP_ERROR("IMPOSSIBLE MOVE"),
	STEP_NOT_PERMITTED("MOVE NOT PERMITTED"),
	OTHER_PLAYER_FIELD("YOU CAN'T MOVE INTO OTHER PLAYER CASES"),
	COLOR_ON_COLOR("THIS CASE ISN'T EMPTY"),
	NEED_A_RING("YOU CAN'T PUT A MARK ON AN EMPTY CASE"),
	IMPOSSIBLE("IMPOSSIBLE MOVE"),
	NOT_YOUR_RING_MARK("NOT YOUR RING/MARK"),
	FULL_CASE("CASE IS FULL"),
	GAME_ENDED("THE GAME IS FINISHED");
	
	private final String message;
	TypeException(String message){
		this.message = message;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
