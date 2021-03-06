package com.dengke.servicebg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"com.dengke"})
@MapperScan("com.dengke.dao.**")
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@EnableAsync
public class ServiceBgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBgApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

//    @Bean
//    public EmbeddedServletContainerCustomize embeddedServletContainerCustomizer() {
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer container) {
//                //设置session超时时间为1分钟
//                container.setSessionTimeout(1, TimeUnit.MINUTES);
//            }
//        };
//    }

}
