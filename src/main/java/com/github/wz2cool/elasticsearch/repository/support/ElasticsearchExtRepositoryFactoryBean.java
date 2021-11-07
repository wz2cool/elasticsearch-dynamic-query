package com.github.wz2cool.elasticsearch.repository.support;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

/**
 * @author Frank
 * @date 2021/11/07
 **/
public class ElasticsearchExtRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
        extends ElasticsearchRepositoryFactoryBean<T, S, ID> {

    private ElasticsearchOperations operations;

    @Override
    public void setElasticsearchOperations(ElasticsearchOperations operations) {
        super.setElasticsearchOperations(operations);
        this.operations = operations;
    }

    /**
     * Creates a new {@link RepositoryFactoryBeanSupport} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    protected ElasticsearchExtRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return new ElasticsearchExtRepositoryFactory(operations);
    }
}
