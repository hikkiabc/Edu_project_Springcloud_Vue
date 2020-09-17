package com.gg.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class BackUser {

    private Integer id;
    private String userName;
    private String password;
    private String token;
}
