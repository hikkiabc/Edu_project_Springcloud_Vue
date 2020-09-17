package com.gg.beans;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Banner implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime=new Date();
}
