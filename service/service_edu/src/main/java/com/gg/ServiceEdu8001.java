package com.gg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
//@ComponentScan({"com.gg.config"})
public class ServiceEdu8001 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceEdu8001.class,args);
    }

}
