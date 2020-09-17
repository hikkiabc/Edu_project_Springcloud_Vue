package com.gg.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Permission {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer pId;
    private String lv;
    private String name;
    private String path;
    @Transient
    private List<Permission> children=new ArrayList<>();
}
