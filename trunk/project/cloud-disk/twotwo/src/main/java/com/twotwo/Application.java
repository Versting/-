package com.twotwo;

import cn.hutool.crypto.digest.DigestUtil;
import com.twotwo.config.CorsConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@MapperScan("com.twotwo.mapper")
@SpringBootApplication
@Import(CorsConfig.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}