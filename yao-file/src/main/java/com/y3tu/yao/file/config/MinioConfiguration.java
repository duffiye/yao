package com.y3tu.yao.file.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MinioConfiguration {

    @Resource
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() throws Exception {
        return new MinioClient(minioProperties.getEndpoint(), minioProperties.getAccessKey(), minioProperties.getSecretKey());
    }
}
