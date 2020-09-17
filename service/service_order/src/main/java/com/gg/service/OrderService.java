package com.gg.service;

import com.gg.beans.CourseOrder;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    CourseOrder saveOrder(Integer courseId, HttpServletRequest valueOf);

    CourseOrder getOrderByNo(String orderNo);
}
