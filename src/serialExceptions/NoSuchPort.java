package serialExceptions;

import gnu.io.NoSuchPortException;

public class NoSuchPort extends NoSuchPortException {
    private static final long serialVersionUID = 3L;

    public NoSuchPort(){}
    public String toString(){
        return "Cannot find that Port!";
    }
}
