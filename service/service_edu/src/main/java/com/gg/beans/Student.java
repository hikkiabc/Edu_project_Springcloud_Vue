package com.gg.beans;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String studentName;
    @ManyToOne
    @JoinColumn
    private ClassForStudent classForStudent;
}
