package com.gg.feign;

import org.springframework.stereotype.Component;

@Component
public class OosFeignImp implements OosFeign {
    @Override
    public Object test(String id) {
        return "hystrix";
    }
}
