package com.gg.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
public class ClassForStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String className;
    @OneToMany(mappedBy = "classForStudent")
    private List<Student> studentList;
}
