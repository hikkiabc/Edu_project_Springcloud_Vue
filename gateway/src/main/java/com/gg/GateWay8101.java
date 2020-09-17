package com.gg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GateWay8101 {
    public static void main(String[] args) {
        SpringApplication.run(GateWay8101.class,args);
    }
}
