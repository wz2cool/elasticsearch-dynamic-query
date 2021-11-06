package com.github.wz2cool.elasticsearch.test.mapper;

import com.github.wz2cool.elasticsearch.repository.ElasticsearchExtRepository;
import com.github.wz2cool.elasticsearch.test.model.StudentES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StudentEsQueryMapper extends ElasticsearchExtRepository<StudentES, Long> {
}
