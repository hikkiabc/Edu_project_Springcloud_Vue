package com.gg.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String pName;
    @ManyToMany
    @JoinTable(name = "emp_project",uniqueConstraints = {@UniqueConstraint(columnNames = {"p_id","e_id"})},
    joinColumns = {@JoinColumn(name = "p_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "e_id",referencedColumnName = "id")})
    private List<Emp> empList=new ArrayList<>();
}
