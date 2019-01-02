package com.ce.notebook.raspberryPi.entity;

import java.util.Arrays;

/**
 * 发送给raspberryPi的信号
 *
 * @author: ce
 * @create: 2018-11-22 21:42
 **/
public class PiSignal {

    SignalType signalType;
    Byte[] data;

    public PiSignal () {
    }

    public PiSignal (SignalType signalType) {
        this.signalType = signalType;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public void setSignalType(SignalType signalType) {
        this.signalType = signalType;
    }

    public Byte[] getData() {
        return data;
    }

    public void setData(Byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PiSignal{" +
                "signalType=" + signalType +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
