package com.gg.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class SubChapter {
    @Id
    @GeneratedValue
    private Integer id;
    private String sort;
    private String name;
    private String courseId;
    private Integer ChapterId;
    private String free;
}
