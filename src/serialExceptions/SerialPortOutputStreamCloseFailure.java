package serialExceptions;

import gnu.io.SerialPortEventListener;

import java.io.IOException;

public class SerialPortOutputStreamCloseFailure extends IOException {
    private static final long serialVersionUID = 6L;
    public SerialPortOutputStreamCloseFailure(){}

    @Override
    public String toString() {
        return "Fail to close port Output Stream!";
    }
}
