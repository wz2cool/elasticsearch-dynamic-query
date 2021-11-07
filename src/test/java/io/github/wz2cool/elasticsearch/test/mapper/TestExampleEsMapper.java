package io.github.wz2cool.elasticsearch.test.mapper;

import io.github.wz2cool.elasticsearch.repository.ElasticsearchExtRepository;
import io.github.wz2cool.elasticsearch.test.model.TestExampleES;

public interface TestExampleEsMapper extends ElasticsearchExtRepository<TestExampleES, Long> {
}
