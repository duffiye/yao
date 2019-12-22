package com.y3tu.yao.upms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: UpmsServerApplication
 * Description:
 * date: 2019/12/22 15:15
 *
 * @author zht
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.y3tu.yao")
@MapperScan("com.y3tu.yao.*.mapper")
public class UpmsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmsServerApplication.class, args);
    }
}
