package com.pro.www;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(value = "com.pro.www.mapper")
public class MealOrderingOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MealOrderingOnlineApplication.class, args);
        log.info("[INFO] 原神，启动！");
    }

}
