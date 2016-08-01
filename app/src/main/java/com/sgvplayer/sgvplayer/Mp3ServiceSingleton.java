package com.sgvplayer.sgvplayer;

import com.sgvplayer.sgvplayer.model.mp3Service.Mp3Service;

/**
 * Created by Alvaro on 01/08/2016.
 */
public class Mp3ServiceSingleton {

    private static Mp3ServiceSingleton instance;
    private Mp3Service service;

    private Mp3ServiceSingleton(Mp3Service service) {
        this.service = service;
    }

    public static Mp3ServiceSingleton getInstance() {
        return instance;
    }

    public static void init(Mp3Service service) {
        instance = new Mp3ServiceSingleton(service);
    }

    public Mp3Service getService() {
        return service;
    }

}