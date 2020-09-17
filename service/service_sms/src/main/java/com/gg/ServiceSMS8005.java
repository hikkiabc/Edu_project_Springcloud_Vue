package com.gg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ServiceSMS8005 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSMS8005.class,args);
    }
}
