package com.gg.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "service-oss",fallback = OosFeignImp.class)
@Component
public interface OosFeign {
    @PostMapping("/oss/test/{id}")
    public Object test(@PathVariable String id);
}
