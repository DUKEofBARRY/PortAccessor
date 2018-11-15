package serialExceptions;

import java.io.IOException;

public class SerialPortInputStreamCloseFailure extends IOException {
    private static final long serialVersionUID = 8L;

    public SerialPortInputStreamCloseFailure(){}

    @Override
    public String toString() {
        return "Fail to close input of the port!";
    }
}
