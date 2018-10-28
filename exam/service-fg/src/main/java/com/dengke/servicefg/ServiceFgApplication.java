package com.dengke.servicefg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.dengke.dao.**")
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@EnableAsync
public class ServiceFgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFgApplication.class, args);
    }
}
