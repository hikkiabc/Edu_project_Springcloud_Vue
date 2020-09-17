package com.gg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Service_Oos8002 {
    public static void main(String[] args) {
        SpringApplication.run(Service_Oos8002.class,args);
    }
}
