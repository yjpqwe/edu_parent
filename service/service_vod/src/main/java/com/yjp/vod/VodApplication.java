package com.yjp.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//因为不用数据库，所以排除
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.yjp"})
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class,args);
    }
}
