package com.ce.notebook.raspberryPi.entity;

/**
 * 发送给raspberryPi的信号
 *
 * @author: ce
 * @create: 2018-11-22 21:36
 **/
public enum SignalType {
    NOTHING(0x00, "nothing"),
    HUMITURE(0x01, "humiture"),
    CURRENT_TIME(0x02, "currentTime"),
    SHOW_STRING(0x03, "showString"),
    HEARTBEAT(0x04, "heartbeat"),
    RELAY(0x05, "relay")
    ;

    Integer signal;
    String name;

    SignalType(Integer signal) {
        this.signal = signal;
    }

    SignalType(Integer signal, String name) {
        this.signal = signal;
        this.name = name;
    }

    public Integer getSignal() {
        return signal;
    }

    public void setSignal(Integer signal) {
        this.signal = signal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
     *
     * @author ce
     * @date 18-11-26 上午8:18
     * @param [signal]
     * @return com.ce.notebook.raspberryPi.entity.SignalType
    */
    public static SignalType getSignalType (Integer signal) {

        for (SignalType signalType : SignalType.values()) {
            if (signalType.getSignal().equals(signal)) {
                return signalType;
            }
        }
        return null;
    }

}
