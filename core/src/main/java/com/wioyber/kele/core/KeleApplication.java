package com.wioyber.kele.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cjg
 * @since 2023/1/6
 */
@SpringBootApplication(scanBasePackages = {"com.wioyber.kele.core"})
@MapperScan({"com.wioyber.kele.core.dao"})
public class KeleApplication {
    public static void main(String[] args) {
        SpringApplication.run(KeleApplication.class,args);
    }
}
