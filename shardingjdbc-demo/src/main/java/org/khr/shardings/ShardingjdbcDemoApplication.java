package org.khr.shardings;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.khr.shardings.mapper")
public class ShardingjdbcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingjdbcDemoApplication.class, args);
    }

}
