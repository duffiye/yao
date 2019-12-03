package com.y3tu.yao.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 授权服务
 *
 * @author y3tu
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.y3tu.yao.feign.client"})
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}
