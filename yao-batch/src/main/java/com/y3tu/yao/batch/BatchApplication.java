package com.y3tu.yao.batch;

import com.y3tu.yao.batch.config.HelloWorldTaskConfigurer;
import com.y3tu.yao.batch.listener.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.logging.Logger;

/**
 * ClassName: BatchApplication
 * Description:
 * date: 2019/12/3 23:47
 *
 * @author zht
 */
public class BatchApplication {
    private final static Logger LOGGER = Logger.getLogger(BatchApplication.class.getName());

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
        SpringApplication.run(BatchApplication.class, args);
    }

    @Component
    public static class HelloWorldApplicationRunner implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments arg0) throws Exception {
            LOGGER.info("Hello World from Spring Cloud Task!");
        }
    }
}
