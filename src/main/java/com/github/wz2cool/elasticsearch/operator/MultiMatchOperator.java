package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.PropertyInfo;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;

import java.util.HashMap;
import java.util.Map;

public class MultiMatchOperator<T> {

    private static final float DEFAULT_BOOST = 1.0f;
    private final Map<String, Float> fieldMap = new HashMap<>();

    public MultiMatchOperator(GetStringPropertyFunction<T>[] getPropertyFuncs) {
        for (GetStringPropertyFunction<T> getPropertyFunc : getPropertyFuncs) {
            field(getPropertyFunc);
        }
    }

    /**
     * See also {@link MultiMatchQueryBuilder#field(String field)}
     */
    public MultiMatchOperator<T> field(GetStringPropertyFunction<T> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        fieldMap.put(columnInfo.getColumnName(), DEFAULT_BOOST);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#field(String field, float boost)}
     */
    public MultiMatchOperator<T> field(GetStringPropertyFunction<T> getPropertyFunc, float boost) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        fieldMap.put(columnInfo.getColumnName(), boost);
        return this;
    }

    private <R> ColumnInfo getColumnInfo(GetPropertyFunction<T, R> getPropertyFunc) {
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPropertyFunc);
        return EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
    }
}
