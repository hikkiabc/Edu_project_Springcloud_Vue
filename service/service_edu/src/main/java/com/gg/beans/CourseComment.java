package com.gg.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class CourseComment {
    @Id
    @GeneratedValue
    private Integer id;
    private String content;
    private Integer userId;
    private Integer courseId;
    private Integer pid;
    private String replyTo;
    @Transient
    private List<CourseComment> children=new ArrayList<>();
    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss"/*, timezone = "GMT+8"*/)
    private Date createDate=new Date();
}
