package com.gg.feign;

import com.gg.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Component
@FeignClient("service-user-center")
public interface UserFeign {

    @GetMapping("/user/login/{id}")
    public CommonResult getUserByUserId( @PathVariable String id) ;

}
