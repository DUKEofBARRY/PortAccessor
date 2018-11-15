package serialExceptions;

import java.io.IOException;

public class SendDataToSerialPortFailure extends IOException {
    private static final long serialVersionUID = 5L;
    public SendDataToSerialPortFailure(){}

    @Override
    public String toString() {
        return "Fail to send order to port";
    }
}
