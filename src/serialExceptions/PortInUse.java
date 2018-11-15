package serialExceptions;

import gnu.io.PortInUseException;

public class PortInUse extends PortInUseException {
    private static final long serialVersionUID = 4L;

    public PortInUse(){}

    @Override
    public String toString() {
        return "The port is in using, please try letter!";
    }
}
