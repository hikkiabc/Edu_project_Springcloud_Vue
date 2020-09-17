package com.gg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Service_Statistics8008 {
    public static void main(String[] args) {
        SpringApplication.run(Service_Statistics8008.class,args);
    }
}
