package com.gg.mapper;

import com.gg.beans.CourseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMapper extends JpaRepository<CourseOrder,Integer> {
    CourseOrder findByOrderNo(String orderNo);
}
