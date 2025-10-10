package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.core.HighlightResultMapper;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.lambda.GetCommonPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.PropertyInfo;
import com.github.wz2cool.elasticsearch.model.QueryMode;
import com.github.wz2cool.elasticsearch.query.function.FilterOperators;
import com.github.wz2cool.elasticsearch.query.function.FunctionBoostMode;
import com.github.wz2cool.elasticsearch.query.function.FunctionScoreFunction;
import com.github.wz2cool.elasticsearch.query.function.FunctionScoreMode;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.RandomScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.WeightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * @author Frank
 **/
public abstract class BaseDynamicQuery<T, S extends BaseFilterGroup<T, S>> extends BaseFilterGroup<T, S> implements IElasticsearchQuery {

    protected Class<T> clazz;
    protected final HighlightResultMapper highlightResultMapper = new HighlightResultMapper();
    protected final HighlightBuilder highlightBuilder = new HighlightBuilder();
    protected final List<SortBuilder> sortBuilders = new ArrayList<>();
    protected QueryMode queryMode;
    protected String route;

    // Function Score 相关字段
    protected List<FunctionScoreFunction> functionScoreFunctions = new ArrayList<>();
    protected CombineFunction boostMode = CombineFunction.MULTIPLY;
    protected FunctionScoreQuery.ScoreMode scoreMode = FunctionScoreQuery.ScoreMode.MULTIPLY;
    protected Float maxBoost;

    private String[] selectedColumns = new String[]{};
    private String[] ignoredColumns = new String[]{};

    public String[] getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(String[] selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public String[] getIgnoredColumns() {
        return ignoredColumns;
    }

    public void setIgnoredColumns(String[] ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    public S scoreMapping(BiConsumer<T, Float> setScorePropertyFunc) {
        highlightResultMapper.registerScoreMapping(this.clazz, setScorePropertyFunc);
        return (S) this;
    }

    public S highlightMapping(GetPropertyFunction<T, String> getSearchPropertyFunc,
                              BiConsumer<T, String> setHighLightPropertyFunc) {
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getSearchPropertyFunc);
        final ColumnInfo columnInfo = EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
        highlightBuilder.field(columnInfo.getColumnName());
        highlightResultMapper.registerHitMapping(this.clazz, getSearchPropertyFunc, setHighLightPropertyFunc);
        return (S) this;
    }

    public S orderByScore(SortOrder sortOrder) {
        return orderByScore(true, sortOrder);
    }

    public S orderByScore(boolean enable, SortOrder sortOrder) {
        if (enable) {
            final ScoreSortBuilder order = new ScoreSortBuilder().order(sortOrder);
            this.sortBuilders.add(order);
        }
        return (S) this;
    }

    public S orderBy(GetCommonPropertyFunction<T> getPropertyFunc, SortOrder sortOrder) {
        return orderBy(true, getPropertyFunc, sortOrder);
    }

    public S orderBy(boolean enable, GetCommonPropertyFunction<T> getPropertyFunc, SortOrder sortOrder) {
        if (enable) {
            final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPropertyFunc);
            ColumnInfo columnInfo = EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
            final FieldSortBuilder order = new FieldSortBuilder(columnInfo.getColumnName()).order(sortOrder);
            this.sortBuilders.add(order);
        }
        return (S) this;
    }

    @SafeVarargs
    public final S select(GetPropertyFunction<T, Object>... getPropertyFunctions) {
        String[] newSelectProperties = new String[getPropertyFunctions.length];
        for (int i = 0; i < getPropertyFunctions.length; i++) {
            newSelectProperties[i] = getColumnName(getPropertyFunctions[i]);
        }
        this.addSelectedColumns(newSelectProperties);
        return (S) this;
    }

    @SafeVarargs
    public final S ignore(GetPropertyFunction<T, Object>... getPropertyFunctions) {
        String[] newIgnoreProperties = new String[getPropertyFunctions.length];
        for (int i = 0; i < getPropertyFunctions.length; i++) {
            newIgnoreProperties[i] = getColumnName(getPropertyFunctions[i]);
        }
        this.ignoreSelectedColumns(newIgnoreProperties);
        return (S) this;
    }

    public HighlightResultMapper getHighlightResultMapper() {
        return highlightResultMapper;
    }

    public HighlightBuilder getHighlightBuilder() {
        return highlightBuilder;
    }

    public List<SortBuilder> getSortBuilders() {
        return sortBuilders;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public QueryMode getQueryMode() {
        return queryMode;
    }

    /**
     * 添加权重函数
     *
     * @param weight 权重值
     * @return 当前查询对象
     */
    public S weightFunction(double weight) {
        return weightFunction(true, weight);
    }

    public S weightFunction(boolean enable, double weight) {
        if (enable) {
            WeightBuilder weightBuilder = new WeightBuilder().setWeight((float) weight);
            this.functionScoreFunctions.add(new FunctionScoreFunction(weightBuilder));
        }

        return (S) this;
    }

    /**
     * 添加带过滤条件的权重函数（基于字段值判断）
     *
     * @param getPropertyFunc 字段获取函数
     * @param operator 操作符
     * @param value 比较值
     * @param weight 权重值
     * @return 当前查询对象
     */
    public <R> S weightFunction(GetPropertyFunction<T, R> getPropertyFunc,
                                FilterOperators operator,
                                R value,
                                double weight) {
       return weightFunction(true, getPropertyFunc, operator, value, weight);
    }

    public <R> S weightFunction(boolean enable,GetPropertyFunction <T, R> getPropertyFunc,
                                FilterOperators operator,
                                R value,
                                double weight) {
        if(enable){
            // 构建过滤条件
            QueryBuilder filter = createFilterQuery(getPropertyFunc, operator, value);
            WeightBuilder weightBuilder = new WeightBuilder().setWeight((float) weight);
            this.functionScoreFunctions.add(new FunctionScoreFunction(filter, weightBuilder));
        }

        return (S) this;
    }

    /**
     * 添加字段值因子函数
     *
     * @param getPropertyFunc 字段获取函数
     * @param factor 因子
     * @return 当前查询对象
     */
    public <R> S fieldValueFactorFunction(GetPropertyFunction<T, R> getPropertyFunc, double factor) {
        return fieldValueFactorFunction(true, getPropertyFunc, factor);
    }

    public <R> S fieldValueFactorFunction(boolean enable, GetPropertyFunction<T, R> getPropertyFunc, double factor) {
        if(enable){
            String fieldName = getColumnName(getPropertyFunc);
            FieldValueFactorFunctionBuilder functionBuilder = new FieldValueFactorFunctionBuilder(fieldName)
                    .factor((float) factor);
            this.functionScoreFunctions.add(new FunctionScoreFunction(functionBuilder));
        }

        return (S) this;
    }

    /**
     * 添加带过滤条件的字段值因子函数
     *
     * @param getPropertyFunc 字段获取函数
     * @param operator 操作符
     * @param value 比较值
     * @param factor 因子
     * @return 当前查询对象
     */
    public <R> S fieldValueFactorFunction(GetPropertyFunction<T, R> getPropertyFunc,
                                          FilterOperators operator,
                                          R value,
                                          double factor) {
        return fieldValueFactorFunction(true, getPropertyFunc, operator, value, factor);
    }

    public <R> S fieldValueFactorFunction(boolean enable,GetPropertyFunction<T, R> getPropertyFunc,
                                          FilterOperators operator,
                                          R value,
                                          double factor) {
        if(enable){
            // 构建过滤条件
            QueryBuilder filter = createFilterQuery(getPropertyFunc, operator, value);
            String fieldName = getColumnName(getPropertyFunc);
            FieldValueFactorFunctionBuilder functionBuilder = new FieldValueFactorFunctionBuilder(fieldName)
                    .factor((float) factor);
            this.functionScoreFunctions.add(new FunctionScoreFunction(filter, functionBuilder));
        }

        return (S) this;
    }

    /**
     * 添加随机函数
     *
     * @return 当前查询对象
     */
    public S randomFunction() {
       return randomFunction(true);
    }

    public S randomFunction(boolean  enable) {
        if(enable){
            RandomScoreFunctionBuilder functionBuilder = new RandomScoreFunctionBuilder();
            this.functionScoreFunctions.add(new FunctionScoreFunction(functionBuilder));
        }

        return (S) this;
    }

    /**
     * 添加带过滤条件的随机函数
     *
     * @param getPropertyFunc 字段获取函数
     * @param operator 操作符
     * @param value 比较值
     * @return 当前查询对象
     */
    public <R> S randomFunction(GetPropertyFunction<T, R> getPropertyFunc,
                                FilterOperators operator,
                                R value) {
       return randomFunction(true, getPropertyFunc, operator, value);
    }

    public <R> S randomFunction(boolean enable,GetPropertyFunction<T, R> getPropertyFunc,
                                FilterOperators operator,
                                R value) {
        if(enable){
            // 构建过滤条件
            QueryBuilder filter = createFilterQuery(getPropertyFunc, operator, value);
            RandomScoreFunctionBuilder functionBuilder = new RandomScoreFunctionBuilder();
            this.functionScoreFunctions.add(new FunctionScoreFunction(filter, functionBuilder));
        }

        return (S) this;
    }

    /**
     * 设置 Boost Mode
     *
     * @param boostMode boost模式
     * @return 当前查询对象
     */
    public S boostMode(FunctionBoostMode boostMode) {
        return boostMode(true, boostMode);
    }

    public S boostMode(boolean enable,FunctionBoostMode boostMode) {
        if(enable){
            switch (boostMode) {
                case REPLACE:
                    this.boostMode = CombineFunction.REPLACE;
                    break;
                case SUM:
                    this.boostMode = CombineFunction.SUM;
                    break;
                case AVG:
                    this.boostMode = CombineFunction.AVG;
                    break;
                case MAX:
                    this.boostMode = CombineFunction.MAX;
                    break;
                case MIN:
                    this.boostMode = CombineFunction.MIN;
            }
        }

        return (S) this;
    }

    /**
     * 设置 Score Mode
     *
     * @param scoreMode score模式
     * @return 当前查询对象
     */
    public S scoreMode(FunctionScoreMode scoreMode) {
       return scoreMode(true, scoreMode);
    }

    public S scoreMode(boolean enable,FunctionScoreMode scoreMode) {
        if(enable){
            switch (scoreMode) {
                case MULTIPLY:
                    this.scoreMode = FunctionScoreQuery.ScoreMode.MULTIPLY;
                    break;
                case SUM:
                    this.scoreMode = FunctionScoreQuery.ScoreMode.SUM;
                    break;
                case AVG:
                    this.scoreMode = FunctionScoreQuery.ScoreMode.AVG;
                    break;
                case MAX:
                    this.scoreMode = FunctionScoreQuery.ScoreMode.MAX;
                    break;
                case MIN:
                    this.scoreMode = FunctionScoreQuery.ScoreMode.MIN;
                    break;
                case FIRST:
                    this.scoreMode = FunctionScoreQuery.ScoreMode.FIRST;
                    break;
                default:
                    this.scoreMode = FunctionScoreQuery.ScoreMode.MULTIPLY;
            }
        }

        return (S) this;
    }

    /**
     * 设置最大 Boost 值
     *
     * @param maxBoost 最大boost值
     * @return 当前查询对象
     */
    public S maxBoost(double maxBoost) {
       return maxBoost(true, maxBoost);
    }

    public S maxBoost(boolean enable,double maxBoost) {
        if(enable){
            this.maxBoost = (float) maxBoost;
        }
        return (S) this;
    }

    @Override
    public NativeSearchQuery buildNativeSearch() {
        NativeSearchQueryBuilder esQuery = new NativeSearchQueryBuilder();
        if (StringUtils.isNotBlank(route)) {
            esQuery.withRoute(route);
        }
        if (getQueryMode() == QueryMode.QUERY) {
            QueryBuilder queryBuilder = CollectionUtils.isEmpty(functionScoreFunctions)
                    ? getFilterQuery()
                    : buildFunctionScoreQuery();
            esQuery.withQuery(queryBuilder);
        } else {
            esQuery.withFilter(getFilterQuery());
        }
        for (SortBuilder sortBuilder : getSortBuilders()) {
            esQuery.withSort(sortBuilder);
        }
        final Optional<SourceFilter> sourceFilterOptional = getSourceFilter();
        sourceFilterOptional.ifPresent(esQuery::withSourceFilter);
        esQuery.withHighlightBuilder(getHighlightBuilder());
        return esQuery.build();
    }

    private QueryBuilder buildFunctionScoreQuery() {
        QueryBuilder baseQuery = getFilterQuery();

        // 构建 FilterFunctionBuilder 数组
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] functionBuilders =
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[functionScoreFunctions.size()];
        for (int i = 0; i < functionScoreFunctions.size(); i++) {
            FunctionScoreFunction function = functionScoreFunctions.get(i);
            if (function.getFilter() != null) {
                functionBuilders[i] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        function.getFilter(),
                        function.getFunctionBuilder());
            } else {
                functionBuilders[i] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        function.getFunctionBuilder());
            }
        }

        // 创建并配置 FunctionScoreQueryBuilder
        FunctionScoreQueryBuilder functionScoreQueryBuilder =
                new FunctionScoreQueryBuilder(baseQuery, functionBuilders);

        functionScoreQueryBuilder.boostMode(boostMode);
        functionScoreQueryBuilder.scoreMode(scoreMode);
        if (maxBoost != null) {
            functionScoreQueryBuilder.maxBoost(maxBoost);
        }

        return functionScoreQueryBuilder;
    }

    protected <R> QueryBuilder createFilterQuery(GetPropertyFunction<T, R> getPropertyFunc,
                                                 FilterOperators operator,
                                                 R value) {
        String fieldName = getColumnName(getPropertyFunc);
        switch (operator) {
            case NOT_EQUAL:
                return QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery(fieldName, value));
            case GREATER_THAN:
                return QueryBuilders.rangeQuery(fieldName).gt(value);
            case LESS_THAN:
                return QueryBuilders.rangeQuery(fieldName).lt(value);
            case GREATER_THAN_EQUAL:
                return QueryBuilders.rangeQuery(fieldName).gte(value);
            case LESS_THAN_EQUAL:
                return QueryBuilders.rangeQuery(fieldName).lte(value);
            case CONTAINS:
                return QueryBuilders.matchQuery(fieldName, value);
            case STARTS_WITH:
                return QueryBuilders.prefixQuery(fieldName, value.toString());
            case EQUAL:
            default:
                return QueryBuilders.termQuery(fieldName, value);
        }
    }

    public void addSelectedColumns(String... newSelectedProperties) {
        setSelectedColumns(ArrayUtils.addAll(selectedColumns, newSelectedProperties));
    }

    public void ignoreSelectedColumns(String... newIgnoreProperties) {
        setIgnoredColumns(ArrayUtils.addAll(ignoredColumns, newIgnoreProperties));
    }

    private Optional<SourceFilter> getSourceFilter() {
        if (ArrayUtils.isEmpty(this.selectedColumns) && ArrayUtils.isEmpty(this.ignoredColumns)) {
            return Optional.empty();
        }

        final FetchSourceFilter fetchSourceFilter = new FetchSourceFilter(this.selectedColumns, this.ignoredColumns);
        return Optional.of(fetchSourceFilter);
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
