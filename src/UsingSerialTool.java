import Util.ShowUtils;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
//import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import serialExceptions.*;

import java.util.*;

public class UsingSerialTool {

    private static SerialPort sp;

    public UsingSerialTool(){}
    public void printPortName(){
        ArrayList al = SerialTool.findPort();
        System.out.println("**********************************");
        System.out.println("size: " + al.size());
        for(int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }
    }

    public SerialPort openPort() throws SerialPortParameterFailure, NoSuchPort, PortInUse, NotASerialPort {
        return SerialTool.openPort("COM3", 115200);
    }

    public void sendMessage(SerialPort sp, String hex) throws SerialPortOutputStreamCloseFailure, SendDataToSerialPortFailure {
        Hex2ByteConverter hbc = new Hex2ByteConverter();
        byte[] order = hbc.hexConverter(hex);
        SerialTool.sendToPort(sp, order);
    }

    public void readMessage(SerialPort sp) throws ReadFromSerialPortFailure, SerialPortInputStreamCloseFailure, TooManyListeners {
        try {
            SerialTool.addListener(sp, new SerialListener());
        } catch (TooManyListeners tooManyListeners) {
            tooManyListeners.printStackTrace();
        }
    }

    private class SerialListener implements SerialPortEventListener {
        /**
         * 处理监控到的串口事件
         */
        public void serialEvent(SerialPortEvent serialPortEvent) {

            switch (serialPortEvent.getEventType()) {

                case SerialPortEvent.BI: // 10 通讯中断
                    //ShowUtils.errorMessage("与串口设备通讯中断");
                    System.out.println("Communication Disconnected");
                    break;

                case SerialPortEvent.OE: // 7 溢位（溢出）错误

                case SerialPortEvent.FE: // 9 帧错误

                case SerialPortEvent.PE: // 8 奇偶校验错误

                case SerialPortEvent.CD: // 6 载波检测

                case SerialPortEvent.CTS: // 3 清除待发送数据

                case SerialPortEvent.DSR: // 4 待发送数据准备好了

                case SerialPortEvent.RI: // 5 振铃指示

                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
                    break;

                case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                    byte[] data = null;
                    try {
                        if (sp == null) {//sp == null
                            //ShowUtils.errorMessage("串口对象为空！监听失败！");
                            System.out.println("Fail to Listening");
                        } else {
                            // 读取串口数据
                            data = SerialTool.readFromPort(sp);
                            Hex2ByteConverter.bytesConverter(data);
                        }
                    } catch (Exception e) {
                        ShowUtils.errorMessage(e.toString());
                        // 发生读取错误时显示错误信息后退出系统
                        System.exit(0);
                    }
                    break;
            }
        }
    }

    public static void main(String[] arg) throws SerialPortParameterFailure, NoSuchPort, PortInUse, NotASerialPort, SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure, ReadFromSerialPortFailure, SerialPortInputStreamCloseFailure, TooManyListeners {
        UsingSerialTool ust = new UsingSerialTool();
        ust.printPortName();
        sp = ust.openPort();
        ust.sendMessage(sp, "FF FF B6 25 00 10 FE FE");
        //ust.readMessage(sp);
        SerialTool.closePort(sp);
    }
   /*public static void main(String args[]) {
       java.awt.EventQueue.invokeLater(new Runnable() {
           public void run() {
               new MainFrame().setVisible(true);
           }
       });
   }*/
}