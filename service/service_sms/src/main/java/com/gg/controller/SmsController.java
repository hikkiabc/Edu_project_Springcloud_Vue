package com.gg.controller;

import com.gg.service.SmsService;
import com.gg.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    SmsService smsService;
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/getcode/{phone}")
    public CommonResult getCode(@PathVariable String phone) {
        CommonResult commonResult = new CommonResult();

        Object o = redisTemplate.opsForValue().get(phone);
        if (o != null) {
            Long expire = redisTemplate.opsForValue().getOperations().getExpire(phone);
            commonResult.setData("wait "+expire+" s");
            return commonResult;
        }
        int code = smsService.sendMsg(phone);
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        commonResult.setData(code);
        return commonResult;
    }
}
