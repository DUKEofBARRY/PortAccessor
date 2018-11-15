/**
 * Interface of port control by java and RXRTcomm.jar
 * @author：Barry
 * @time: 2018/4/11/14:02
 * @version: v1.0
 */

import gnu.io.*;
import java.io.*;
import java.util.*;

import serialExceptions.*;

public class SerialTool {
    private static SerialTool serialTool = null;

    static{
        // Initialize a new a SerialTool objects
        if(serialTool == null){
            serialTool = new SerialTool();
        }
    }

    private SerialTool(){}


    public static SerialTool getSerialTool(){
        if(serialTool == null){
            serialTool = new SerialTool();
        }
        return serialTool;
    }


    /**
     * Find all usable port
     * @return ArrayList of port name
     */

    public static final ArrayList<String> findPort(){

        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();

        ArrayList<String> portNameList = new ArrayList<>();

        while(portList.hasMoreElements()){
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }

        return portNameList;
    }

    /**
     * Open the port
     * @param portName
     * @param baudrate
     * @return SerialPort object
     * @throws SerialPortParameterFailure: parameter set failure
     * @throws NotASerialPort: not a port
     * @throws NoSuchPort: port not exist
     * @throws PortInUse: port keep in using
     */
    public static final SerialPort openPort(String portName, int baudrate) throws SerialPortParameterFailure, NotASerialPort, NoSuchPort,PortInUse{
        try{
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

            CommPort commPort = portIdentifier.open(portName, 2000);

            if(commPort instanceof SerialPort){
                SerialPort serialPort = (SerialPort) commPort;

                try{
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                }catch (UnsupportedCommOperationException e){
                    throw new SerialPortParameterFailure();
                }

                return serialPort;
            }

            else{
                throw new NotASerialPort();
            }
        }catch(NoSuchPortException e1){
            throw new NoSuchPort();
        }catch (PortInUseException e2){
            throw new PortInUse();
        }

    }

    /**
     * Close the port
     * @param serialPort: Port need to close
     */
    public static void closePort(SerialPort serialPort){
        if(serialPort != null){
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * Send order to port
     * @param serialPort: Port object receiving data
     * @param order: order need to send
     * @throws SendDataToSerialPortFailure
     * @throws SerialPortOutputStreamCloseFailure
     */
    public static void sendToPort(SerialPort serialPort, byte[] order) throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure{
        OutputStream outputStream = null;
        try{
            outputStream = serialPort.getOutputStream();
            outputStream.write(order);
            outputStream.flush();
        }catch (IOException e) {
            throw new SendDataToSerialPortFailure();
        }finally {
            try{
                if(outputStream != null){
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException e) {
                throw new SerialPortOutputStreamCloseFailure();
            }
        }
    }

    /**
     * Read information from port
     * @param serialPort: port object
     * @return info read from port
     * @throws ReadFromSerialPortFailure
     * @throws SerialPortInputStreamCloseFailure
     */
    public static byte[] readFromPort(SerialPort serialPort) throws ReadFromSerialPortFailure, SerialPortInputStreamCloseFailure{
        InputStream inputStream = null;
        byte[] bytes = null;

        try{
            inputStream = serialPort.getInputStream();
            int bufferLength = inputStream.available();
            while(bufferLength != 0){
                bytes = new byte[bufferLength];
                inputStream.read(bytes);
                bufferLength = inputStream.available();
            }
        } catch (IOException e) {
            throw new ReadFromSerialPortFailure();
        }finally {
            try{
                if(inputStream != null){
                    inputStream.close();
                    inputStream = null;
                }
            } catch (IOException e) {
                throw new SerialPortInputStreamCloseFailure();
            }
        }

        return bytes;
    }

    /**
     * Add actionListener to port
     * @param serialPort: Port object
     * @param listener: action listener
     * @throws TooManyListeners
     */
    public static void addListener(SerialPort serialPort, SerialPortEventListener listener) throws TooManyListeners{
        try{
            serialPort.addEventListener(listener);
            serialPort.notifyOnDataAvailable(true);
            serialPort.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            throw new TooManyListeners();
        }
    }
}

