package com.gg.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="service-user-center")
@Component
public interface UserFeign {
    @GetMapping("/user/logincountbydate")
    public Integer getRegisterCountByDate(@RequestParam("date") String date);

}
