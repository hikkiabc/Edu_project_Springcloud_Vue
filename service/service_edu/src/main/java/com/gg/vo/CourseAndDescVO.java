package com.gg.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseAndDescVO {
    private Integer id;
    private Integer descId;
    private String name;
    private String courseDesc;
    private BigDecimal price;
    private String teacherId;
    private String subjectLv1Id;
    private String subjectLv2Id;
    private String poster;
}
