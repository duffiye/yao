package com.y3tu.yao.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * ClassName: ExecutorConfiguration
 * Description:
 * date: 2019/10/8 10:10
 *
 * @author zht
 */
@Configuration
public class ExecutorConfiguration {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(50);
        threadPoolTaskExecutor.setMaxPoolSize(200);
        threadPoolTaskExecutor.setQueueCapacity(1000);
        threadPoolTaskExecutor.setThreadNamePrefix("Data-Job");
        return threadPoolTaskExecutor;
    }
}
