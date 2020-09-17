package com.gg.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {
    private Boolean Success=true;
    private Object Data;


    public static Map<String,Object> createMap(){

        return new HashMap<String,Object>();
    }

}
