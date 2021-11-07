package io.github.wz2cool.elasticsearch.query.builder;

import io.github.wz2cool.elasticsearch.cache.EntityCache;
import io.github.wz2cool.elasticsearch.helper.CommonsHelper;
import io.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import io.github.wz2cool.elasticsearch.model.ColumnInfo;
import io.github.wz2cool.elasticsearch.model.PropertyInfo;
import org.elasticsearch.index.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;

public interface ExtQueryBuilder {

    default Object getFilterValue(Object value) {
        if (value instanceof Date) {
            return ((Date) value).getTime();
        } else if (value instanceof BigDecimal) {
            return value.toString();
        }
        return value;
    }

    default <T, R> ColumnInfo getColumnInfo(GetPropertyFunction<T, R> getPropertyFunc) {
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPropertyFunc);
        return EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
    }

    QueryBuilder build();
}
