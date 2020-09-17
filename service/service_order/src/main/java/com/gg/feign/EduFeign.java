package com.gg.feign;

import com.gg.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduFeign {

    @GetMapping("/teacher/course/{id}")
    public CommonResult getCourseById(@PathVariable String id) ;
}
