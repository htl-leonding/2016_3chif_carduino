package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.TooManyListenersException;

public class Serial extends Observable implements SerialPortEventListener {

    protected static SerialPort serialPort;

    public SerialPort getSerialPort() {
        return serialPort;
    }

    private static final String PORT_NAMES[] = { "COM3", "COM4" };

    private BufferedReader input;

    private String inputLine;

    public String getInputLine() {
        return inputLine;
    }

    private OutputStream output;

    private static final int TIME_OUT = 2000;

    private static final int DATA_RATE = 115200;

    public void setOutput(String s)  {
        try {
            this.output.write(s.getBytes());
        } catch (IOException ex) {
            System.out.println("Nicht verbunden");
        }
    }

    public void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }
        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
            serialPort.disableReceiveTimeout();
            serialPort.enableReceiveThreshold(1);
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, 
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (PortInUseException | UnsupportedCommOperationException | 
                IOException | TooManyListenersException e) {
            System.err.println(e.toString());
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                inputLine = input.readLine();
                System.out.println(inputLine);
                setChanged();
                notifyObservers();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }
}
