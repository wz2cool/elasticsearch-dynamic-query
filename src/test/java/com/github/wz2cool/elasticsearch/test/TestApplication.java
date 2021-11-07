package com.github.wz2cool.elasticsearch.test;

import com.github.wz2cool.elasticsearch.repository.support.ElasticsearchExtRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.*",
        repositoryFactoryBeanClass = ElasticsearchExtRepositoryFactoryBean.class)
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
