package com.gg.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
public class Course {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private BigDecimal price;
    private String teacherId;
    private String poster;
    private String status="draft";
    private String subjectLv1Id;
    private String subjectLv2Id;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss"/*, timezone = "GMT+8"*/)
    private Date createTime=new Date();
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss"/*, timezone = "GMT+8"*/)
    private Date updateTime=new Date();
    @OneToOne(mappedBy = "course",cascade = CascadeType.ALL)
    private CourseDesc courseDesc;
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", teacherId='" + teacherId + '\'' +
                ", poster='" + poster + '\'' +
                ", status='" + status + '\'' +
                ", subjectLv1Id='" + subjectLv1Id + '\'' +
                ", subjectLv2Id='" + subjectLv2Id + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", courseDesc=" + courseDesc.getCourseDesc() +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }




    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectLv1Id() {
        return subjectLv1Id;
    }

    public void setSubjectLv1Id(String subjectLv1Id) {
        this.subjectLv1Id = subjectLv1Id;
    }

    public String getSubjectLv2Id() {
        return subjectLv2Id;
    }

    public void setSubjectLv2Id(String subjectLv2Id) {
        this.subjectLv2Id = subjectLv2Id;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
//    @JsonBackReference
 @JsonManagedReference
    public CourseDesc getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(CourseDesc courseDesc) {
        this.courseDesc = courseDesc;
    }
}
