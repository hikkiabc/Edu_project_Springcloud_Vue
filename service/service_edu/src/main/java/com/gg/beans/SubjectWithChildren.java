package com.gg.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class SubjectWithChildren {
    private Integer id;
    private Integer pid;
    private String title;
    private List<SubjectWithChildren> children=new ArrayList<>();
}
