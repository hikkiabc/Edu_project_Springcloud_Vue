package com.gg.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer pid;
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    //出参时间格式化
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss"/*, timezone = "GMT+8"*/)
    //入参时，请求报文只需要传入yyyymmddhhmmss字符串进来，则自动转换为Date类型数据
//    @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private Date updateTime=new Date();
    @Temporal(TemporalType.TIMESTAMP)
    //出参时间格式化
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss"/*, timezone = "GMT+8"*/)
    //入参时，请求报文只需要传入yyyymmddhhmmss字符串进来，则自动转换为Date类型数据
//    @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private Date createdTime=new Date();

}
