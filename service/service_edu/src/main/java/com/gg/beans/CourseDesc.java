package com.gg.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;


@Entity
public class CourseDesc {
    @Id
    @GeneratedValue
    private Integer id;
    @Lob
    @Column(columnDefinition="TEXT")
    private String courseDesc;
    @OneToOne
    @JoinColumn(unique = true)

    private Course course;

    @Override
    public String toString() {
        return "CourseDesc{" +
                "id=" + id +
                ", courseDesc='" + courseDesc + '\'' +
                ", course=" + course +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }
     @JsonBackReference
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
