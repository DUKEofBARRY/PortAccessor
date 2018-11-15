package serialExceptions;

public class NotASerialPort extends Exception {
    private static final long serialVersionUID = 2L;

    public NotASerialPort(){}

    public String toString(){
        return "This is not a serial port!";
    }
}
