package com.y3tu.yao.batch.config;

import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;

import javax.sql.DataSource;

/**
 * @version V1.0
 * @ClassName: HelloWorldTaskConfigurer
 * @Description:注入DataSource
 * @author: zouhongtao
 * @date: 2018年10月12日 下午1:45:05
 */
public class HelloWorldTaskConfigurer extends DefaultTaskConfigurer {

    public HelloWorldTaskConfigurer(DataSource dataSource) {
        super(dataSource);
    }
}
