package com.gg.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsServiceImp implements SmsService {
    @Override
    public int sendMsg(String phone) {
        int num = new Random().nextInt(8999) + 1000;
        return num;
    }
}
