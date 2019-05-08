package com.bhw.wechatsell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.bhw.wechatsell.entity.mapper")
@EnableCaching
public class WechatsellApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatsellApplication.class, args);
    }

}
