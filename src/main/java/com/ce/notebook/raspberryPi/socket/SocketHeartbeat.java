package com.ce.notebook.raspberryPi.socket;

import com.ce.notebook.raspberryPi.entity.PiSignal;
import com.ce.notebook.raspberryPi.entity.SignalType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * socket保持连接的心跳检测线程
 *
 * @author: ce
 * @create: 2018-11-25 21:40
 **/
@Component
public class SocketHeartbeat {

    private static final Logger logger = LoggerFactory.getLogger(SocketHeartbeat.class);

    @Autowired
    private OLEDSocket oledSocket;

    /*
     * 心跳检测线程
     * @author ce
     * @date 18-11-26 上午8:26
     * @param []
     * @return void
    */
    @PostConstruct
    public void heartbeat () {

        new Thread(() -> {
            while (true) {
                try {
                    if (!oledSocket.isConnected()) {
                        logger.info("reset OLEDSocket");
                        oledSocket.resetSocket();
                    }

//                    发送心跳信号给socket服务器, 服务器会传会同样的信号
//                    判断服务器传回的信号是否为心跳信号
                    PiSignal receiveSignal = oledSocket.sendAndReceiveSocketSignal(new PiSignal(SignalType.HEARTBEAT));
                    if (receiveSignal.getSignalType() != SignalType.HEARTBEAT) {
                        logger.info("heartbeat fail... (signalType=" + receiveSignal.getSignalType() + ")");
                        throw new IOException("接收心跳错误");
                    } else {
                        logger.info("heartbeat success...");
                    }
                    Thread.sleep(4000);

                } catch (Exception e) {
                    logger.error("心跳异常(heartbeat exception)...", e);
//                    设置标志 为下次循环重置socket做准备
                    oledSocket.closeSocket();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
