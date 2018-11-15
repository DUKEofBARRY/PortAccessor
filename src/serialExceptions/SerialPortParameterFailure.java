package serialExceptions;

public class SerialPortParameterFailure extends Exception{

    private static final long serialVersionUID = 1L;

    public SerialPortParameterFailure(){}

    public String toString(){
        return "Fail to set parameter of port! Have Been Not Open Port Yet";
    }
}
