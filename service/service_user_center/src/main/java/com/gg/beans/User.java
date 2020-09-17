package com.gg.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private  Integer id;
    private String username;
    private String phone;
    private String password;
    private String disabled="0";
    @Transient
    private String code;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate=new Date();
}
