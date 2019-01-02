package com.ce.notebook.raspberryPi.socket;

import com.ce.notebook.raspberryPi.entity.PiSignal;
import com.ce.notebook.raspberryPi.entity.SignalType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;

/**
 * @author: ce
 * @create: 2018-11-21 05:03
 **/
@Service
public class OLEDSocket {

    final static String HOST = "192.168.12.1";
    final static Integer PORT = 12306;

    private Socket socket = null;
    private PrintWriter writer = null;
    private OutputStream byteWriter = null;
    private BufferedReader reader = null;
    private Boolean connected = false;

    private Socket createSocket () throws IOException {
        return new Socket(HOST, PORT);
    }

    private Socket createSocket (String host, Integer port) throws IOException {
        return new Socket(host, port);
    }

    public void resetSocket () throws IOException {
        socket = createSocket();
        byteWriter = socket.getOutputStream();
        writer = new PrintWriter(byteWriter);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        connected = true;
    }

    public void closeSocket () {
        this.connected = false;
        try {
            byteWriter.close();
            writer.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * socket是否连接
     * @author ce
     * @date 18-11-25 下午9:55
     * @param []
     * @return java.lang.Boolean
    */
    public Boolean isConnected () {
        return connected;
    }


    public void sendSocketMessage (PiSignal piSignal) {
        try {
            resetSocket();
            byteWriter.write(piSignal.getSignalType().getSignal());
            byteWriter.flush();
            receiveSocketSignal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSocketSignal (PiSignal piSignal) {
        try {
            byteWriter.write(piSignal.getSignalType().getSignal());
            byteWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PiSignal receiveSocketSignal () {
        PiSignal piSignal = new PiSignal();
        try {
            piSignal.setSignalType(SignalType.getSignalType(reader.read()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return piSignal;
    }

    /*
     * 把发送和接受放到同步方法中,避免和心跳线程冲突
     * @author ce
     * @date 18-12-7 下午11:43
     * @param [piSignal]
     * @return com.ce.notebook.raspberryPi.entity.PiSignal
    */
    public synchronized PiSignal sendAndReceiveSocketSignal (PiSignal piSignal) {
        sendSocketSignal(piSignal);
        return receiveSocketSignal();
    }
}
