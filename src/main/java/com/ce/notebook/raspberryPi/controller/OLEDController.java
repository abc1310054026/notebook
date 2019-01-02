package com.ce.notebook.raspberryPi.controller;

import com.ce.notebook.raspberryPi.entity.PiSignal;
import com.ce.notebook.raspberryPi.socket.OLEDSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * oled屏幕对外接口
 *
 * @author: ce
 * @create: 2018-11-21 05:01
 **/
@RestController
@RequestMapping(value = "/oled")
public class OLEDController {

    @Autowired
    private OLEDSocket socket;

    @RequestMapping(value = "/showType", method = RequestMethod.POST)
    public String showType (@RequestBody PiSignal piSignal) {
        return socket.sendAndReceiveSocketSignal(piSignal).toString();
    }
}
