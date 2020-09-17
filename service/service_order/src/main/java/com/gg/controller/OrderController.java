package com.gg.controller;

import com.gg.beans.CourseOrder;
import com.gg.feign.EduFeign;
import com.gg.service.OrderService;
import com.gg.utils.CommonResult;
import com.gg.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    EduFeign eduFeign;
  @PostMapping("/")
    public CommonResult saveOrder(@RequestBody Map map, HttpServletRequest request){
      System.out.println(request);
      Integer courseId = Integer.valueOf((String) map.get("courseId"));
      CourseOrder courseOrder = orderService.saveOrder(courseId,request);
      CommonResult commonResult = new CommonResult();
      commonResult.setData(courseOrder);
      return commonResult;
  }

  @GetMapping("/{orderNo}")
    public CommonResult getOrderByOrderNo(@PathVariable String orderNo){
     CourseOrder courseOrder = orderService.getOrderByNo(orderNo);
      CommonResult commonResult = new CommonResult();
      commonResult.setData(courseOrder);

      return commonResult;
  }
  @PostMapping("/a")
    public Object test(@RequestBody Map courseId,HttpServletRequest request){
      System.out.println(request);
      System.out.println(courseId);
      return courseId;
  }
}
