package com.gg.beans;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Entity
@Data
public class CourseOrder {
    @Id
    @GeneratedValue
    private Integer id;
    private String orderNo= UUID.randomUUID().toString().replace("-","");
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
    private Integer userId;
    private String username;
    private Integer courseId;
    private String courseName;
    private BigDecimal price;
    private Integer status = 0;

}