package com.github.wz2cool.elasticsearch.mapper;

import com.github.wz2cool.elasticsearch.model.LogicPagingResult;
import com.github.wz2cool.elasticsearch.query.LogicPagingQuery;

/**
 * @author Frank
 **/
public interface SelectByLogicPagingQueryMapper<T> {

    /**
     * select by logic paging
     *
     * @param logicPagingQuery logic paging query
     * @return logic paging result
     */
    LogicPagingResult<T> selectByLogicPaging(LogicPagingQuery<T> logicPagingQuery);
}
