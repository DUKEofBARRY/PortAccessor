package serialExceptions;

public class TooManyListeners extends java.util.TooManyListenersException {
    private static final long serialVersionUID = 9L;
    public TooManyListeners(){}

    @Override
    public String toString() {
        return "Too many listener add to the port";
    }
}
