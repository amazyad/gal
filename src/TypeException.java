public enum TypeException {

    RING_ALREADY_EXIST("A ring already exist in this case"),
    NOT_YOUR_TURN("It is not your turn"),
    CANT_JUMP_OVER_RING("You can't move your ring over another ring"),
    MUST_BE_EMPTY("You can't move a ring on a non empty intersection"),
    NEED_A_RING("YOU CAN'T PUT A MARK ON AN EMPTY CASE"),
    IMPOSSIBLE("IMPOSSIBLE MOVE");

    private final String message;

    TypeException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}