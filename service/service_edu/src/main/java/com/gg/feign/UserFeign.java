package com.gg.feign;

import com.gg.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Component
@FeignClient(value="service-user-center")
public interface UserFeign {
    @PostMapping("/user")
    public List getUsersByIds(@RequestBody Set<Integer> ids);



}
