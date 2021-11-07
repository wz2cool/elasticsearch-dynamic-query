package com.github.wz2cool.elasticsearch.helper;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.lambda.GetLongPropertyFunction;
import com.github.wz2cool.elasticsearch.model.*;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.*;

/**
 * @author Frank
 **/
public final class LogicPagingHelper {

    private LogicPagingHelper() {
    }

    public static <T> Map.Entry<SortDescriptor, QueryBuilder> getPagingSortFilterMap(
            GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, Long startPageId, Long endPageId, UpDown upDown) {
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(pagingPropertyFunc);
        final ColumnInfo columnInfo = EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
        final String columnName = columnInfo.getColumnName();
        SortDescriptor sortDescriptor = new SortDescriptor();
        sortDescriptor.setPropertyName(propertyInfo.getPropertyName());
        sortDescriptor.setSortOrder(sortOrder);
        Map.Entry<SortDescriptor, QueryBuilder> resultMap = new AbstractMap.SimpleEntry<>(sortDescriptor, null);
        if (Objects.isNull(startPageId) && Objects.isNull(endPageId)) {
            return resultMap;
        }
        UpDown useUpDown = UpDown.NONE.equals(upDown) ? UpDown.DOWN : upDown;
        if (UpDown.DOWN.equals(useUpDown) && SortOrder.ASC.equals(sortOrder)) {
            if (Objects.isNull(endPageId)) {
                return resultMap;
            }
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(columnName).gt(endPageId);
            resultMap.setValue(rangeQueryBuilder);
            return resultMap;
        }
        if (UpDown.DOWN.equals(useUpDown) && SortOrder.DESC.equals(sortOrder)) {
            if (Objects.isNull(endPageId)) {
                return resultMap;
            }
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(columnName).lt(endPageId);
            resultMap.setValue(rangeQueryBuilder);
            return resultMap;
        }
        if (UpDown.UP.equals(useUpDown) && SortOrder.ASC.equals(sortOrder)) {
            if (Objects.isNull(startPageId)) {
                return resultMap;
            }
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(columnName).lt(startPageId);
            resultMap.setValue(rangeQueryBuilder);
            // need change direction
            resultMap.getKey().setSortOrder(SortOrder.DESC);
            resultMap.setValue(rangeQueryBuilder);
            return resultMap;
        }
        if (UpDown.UP.equals(useUpDown) && SortOrder.DESC.equals(sortOrder)) {
            if (Objects.isNull(startPageId)) {
                return resultMap;
            }
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(columnName).gt(startPageId);
            // need change direction
            resultMap.getKey().setSortOrder(SortOrder.ASC);
            resultMap.setValue(rangeQueryBuilder);
            return resultMap;
        }
        return resultMap;
    }

    public static <T> Optional<LogicPagingResult<T>> getPagingResult(
            GetLongPropertyFunction<T> pagingPropertyFunc, List<T> dataList, int pageSize, UpDown upDown) {
        int dataSize = dataList.size();
        boolean hasNextPage;
        boolean hasPreviousPage;
        if (UpDown.NONE.equals(upDown)) {
            hasNextPage = dataSize > pageSize;
            hasPreviousPage = false;
        } else if (UpDown.DOWN.equals(upDown)) {
            hasNextPage = dataSize > pageSize;
            hasPreviousPage = true;
        } else {
            if (dataSize < pageSize) {
                return Optional.empty();
            }
            if (dataSize > pageSize) {
                hasPreviousPage = true;
                hasNextPage = true;
            } else {
                hasPreviousPage = false;
                hasNextPage = true;
            }
        }
        Long startPageId = 0L;
        Long endPageId = 0L;
        List<T> pagingDataList = getLogicPagingData(dataList, pageSize, upDown);
        if (!pagingDataList.isEmpty()) {
            startPageId = pagingPropertyFunc.apply(pagingDataList.get(0));
            endPageId = pagingPropertyFunc.apply(pagingDataList.get(pagingDataList.size() - 1));
        }
        LogicPagingResult<T> logicPagingResult = new LogicPagingResult<>();
        logicPagingResult.setHasNextPage(hasNextPage);
        logicPagingResult.setHasPreviousPage(hasPreviousPage);
        logicPagingResult.setStartPageId(startPageId);
        logicPagingResult.setEndPageId(endPageId);
        logicPagingResult.setPageSize(pageSize);
        logicPagingResult.setList(pagingDataList);
        return Optional.of(logicPagingResult);
    }

    private static <T> List<T> getLogicPagingData(List<T> dataList, int pageSize, UpDown upDown) {
        if (dataList.isEmpty()) {
            return new ArrayList<>();
        }
        List<T> result;
        if (dataList.size() <= pageSize) {
            result = new ArrayList<>(dataList);
        } else {
            if (UpDown.UP == upDown) {
                result = dataList.subList(1, dataList.size());
            } else {
                result = dataList.subList(0, dataList.size() - 1);
            }
        }
        return result;
    }
}
