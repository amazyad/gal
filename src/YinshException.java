public class YinshException extends Exception {
    private static final long serialVersionUID = 1L;

    public YinshException(TypeException type) {
        super(type.getMessage());
    }
}
