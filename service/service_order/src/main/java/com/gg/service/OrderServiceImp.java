package com.gg.service;

import com.gg.beans.CourseOrder;
import com.gg.feign.EduFeign;
import com.gg.feign.UserFeign;
import com.gg.mapper.OrderMapper;
import com.gg.utils.CommonResult;
import com.gg.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@Transactional
@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    EduFeign eduFeign;
    @Autowired
    UserFeign userFeign;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public CourseOrder saveOrder(Integer courseId, HttpServletRequest request) {
        String userId = JWTUtil.getUserId(request.getHeader("token"));
        CommonResult courseById = eduFeign.getCourseById(String.valueOf(courseId));
        CommonResult userByToken = userFeign.getUserByUserId(userId);
        Map course = (Map<String, Object>) courseById.getData();
        Map user = (Map<String, Object>) userByToken.getData();
        CourseOrder order = new CourseOrder();
        order.setCourseId((Integer) course.get("id"));
        order.setCourseName((String) course.get("name"));
        order.setPrice(new BigDecimal((Double) course.get("price")) );
        order.setUsername((String) user.get("username"));
        order.setUserId((Integer) user.get("id"));
//        return null;
        return orderMapper.save(order);

    }

    @Override
    public CourseOrder getOrderByNo(String orderNo) {
        return orderMapper.findByOrderNo(orderNo);

    }
}
