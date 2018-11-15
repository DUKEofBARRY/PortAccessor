package serialExceptions;

import java.io.IOException;

public class ReadFromSerialPortFailure extends IOException {
    private static final long serialVersionUID = 7L;
    public ReadFromSerialPortFailure(){}

    @Override
    public String toString() {
        return "Cannot read from port!";
    }
}
