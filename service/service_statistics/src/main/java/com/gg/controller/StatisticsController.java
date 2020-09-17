package com.gg.controller;

import com.gg.beans.Statistics;
import com.gg.feign.UserFeign;
import com.gg.service.StatisticsService;
import com.gg.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@CrossOrigin
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/registercountbydate")
    public CommonResult getRegisterCountByDate(String date){
        Statistics registerCountByDate = statisticsService.getRegisterCountByDate(date);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(registerCountByDate);
        return commonResult;
    }
    @PostMapping("/")
    public CommonResult getStatisticsForChart(@RequestBody Map<String,String> condition){
        String factor = condition.get("factor");

        String min = condition.get("min");
        String max = condition.get("max");
       List/*<Map<String,String>>*/ list= statisticsService.getStatisticsForChart(min,max);
        CommonResult commonResult = new CommonResult();
        commonResult.setData(list);
        return commonResult;
    }
}
