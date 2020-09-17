package com.gg.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Chapter {
    @Id
    @GeneratedValue
    private Integer id;
    private String sort;
    private String name;
    private String courseId;
    @Transient
    private List<SubChapter> children=new ArrayList<>();

}
