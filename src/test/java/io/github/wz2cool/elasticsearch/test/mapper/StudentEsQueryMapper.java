package io.github.wz2cool.elasticsearch.test.mapper;

import io.github.wz2cool.elasticsearch.repository.ElasticsearchExtRepository;
import io.github.wz2cool.elasticsearch.test.model.StudentES;

public interface StudentEsQueryMapper extends ElasticsearchExtRepository<StudentES, Long> {
}
