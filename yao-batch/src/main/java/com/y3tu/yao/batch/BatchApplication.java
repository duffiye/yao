package com.y3tu.yao.batch;

import com.y3tu.yao.batch.config.HelloWorldTaskConfigurer;
import com.y3tu.yao.batch.listener.TaskListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * @version V1.0
 * @ClassName: TaskDemo
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date: 2019年10月9日12:12:36
 */
@SpringBootApplication
@EnableBatchProcessing
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.y3tu.yao.feign")
public class TaskDemo {

    private final static Logger LOGGER = Logger.getLogger(TaskDemo.class.getName());

    @Autowired
    private DataSource dataSource;

    @Bean
    public HelloWorldTaskConfigurer getTaskConfigurer() {
        return new HelloWorldTaskConfigurer(dataSource);
    }

    @Bean
    public TaskListener taskListener() {
        return new TaskListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskDemo.class, args);
    }

    @Component
    public static class HelloWorldApplicationRunner implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments arg0) throws Exception {
            LOGGER.info("Hello World from Spring Cloud Task!");
        }
    }
}
