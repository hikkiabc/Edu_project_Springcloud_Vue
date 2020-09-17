package com.gg.beans;

import lombok.Data;

import java.io.Serializable;
@Data
public class CurrentUser implements Serializable {
    private String userName;
    private String password;
    private String token;

}
