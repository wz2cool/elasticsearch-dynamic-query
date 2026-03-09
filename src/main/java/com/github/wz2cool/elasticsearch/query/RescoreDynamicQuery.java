package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.model.QueryMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScriptScoreQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.rescore.QueryRescorerBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.RescorerQuery;
import org.springframework.data.elasticsearch.core.query.SourceFilter;

import java.util.*;

/**
 * 支持rescore功能的动态查询类
 */
public class RescoreDynamicQuery<T> extends DynamicQuery<T> {

    // Rescore 相关字段
    protected Integer rescoreWindowSize;
    protected String rescoreScript;
    protected Map<String, Object> rescoreScriptParams;
    protected Float rescoreQueryWeight;
    protected Float rescoreWeight;

    public RescoreDynamicQuery() {
        super(null, null, null);
    }

    public RescoreDynamicQuery(Class<T> clazz, QueryMode queryMode, String route) {
        super(clazz, queryMode, route);
    }

    public static <T> RescoreDynamicQuery<T> createQuery(Class<T> clazz) {
        return new RescoreDynamicQuery<>(clazz, QueryMode.QUERY, null);
    }

    public static <T> RescoreDynamicQuery<T> createQuery(Class<T> clazz, QueryMode queryMode) {
        return new RescoreDynamicQuery<>(clazz, queryMode, null);
    }

    public static <T> RescoreDynamicQuery<T> createQuery(Class<T> clazz, String route) {
        return new RescoreDynamicQuery<>(clazz, QueryMode.QUERY, route);
    }

    public static <T> RescoreDynamicQuery<T> createQuery(Class<T> clazz, QueryMode queryMode, String route) {
        return new RescoreDynamicQuery<>(clazz, queryMode, route);
    }

    /**
     * 设置重打分窗口大小
     * @param windowSize 窗口大小
     * @return 当前查询对象
     */
    public RescoreDynamicQuery<T> rescoreWindowSize(int windowSize) {
        this.rescoreWindowSize = windowSize;
        return this;
    }

    /**
     * 设置重打分原始查询权重
     * @param queryWeight 原始查询权重
     * @return 当前查询对象
     */
    public RescoreDynamicQuery<T> rescoreQueryWeight(float queryWeight) {
        this.rescoreQueryWeight = queryWeight;
        return this;
    }

    /**
     * 设置重打分查询权重
     * @param weight 重打分查询权重
     * @return 当前查询对象
     */
    public RescoreDynamicQuery<T> rescoreWeight(float weight) {
        this.rescoreWeight = weight;
        return this;
    }

    /**
     * 设置重打分脚本
     * @param script 脚本内容
     * @param params 脚本参数
     * @return 当前查询对象
     */
    public RescoreDynamicQuery<T> rescoreScript(String script, Map<String, Object> params) {
        this.rescoreScript = script;
        this.rescoreScriptParams = params != null ? params : Collections.emptyMap();
        return this;
    }

    /**
     * 获取重打分窗口大小
     * @return 重打分窗口大小
     */
    public Integer getRescoreWindowSize() {
        return rescoreWindowSize;
    }

    /**
     * 获取重打分脚本
     * @return 重打分脚本
     */
    public String getRescoreScript() {
        return rescoreScript;
    }

    /**
     * 获取重打分脚本参数
     * @return 重打分脚本参数
     */
    public Map<String, Object> getRescoreScriptParams() {
        return rescoreScriptParams;
    }

    /**
     * 获取重打分原始查询权重
     * @return 重打分原始查询权重
     */
    public Float getRescoreQueryWeight() {
        return rescoreQueryWeight;
    }

    /**
     * 获取重打分查询权重
     * @return 重打分查询权重
     */
    public Float getRescoreWeight() {
        return rescoreWeight;
    }
    @Override
    public NativeSearchQuery buildNativeSearch() {
        NativeSearchQuery nativeSearchQuery = super.buildNativeSearch();
        if (this.rescoreWindowSize != null && this.rescoreScript != null) {
            Script script;
            if (this.rescoreScriptParams != null) {
                script = new Script(ScriptType.STORED, (String)null, this.rescoreScript, this.rescoreScriptParams);
            } else {
                script = new Script(ScriptType.STORED, (String)null, this.rescoreScript, Collections.emptyMap());
            }

            ScriptScoreFunctionBuilder scriptScoreFunctionBuilder = new ScriptScoreFunctionBuilder(script);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(QueryBuilders.matchAllQuery(), scriptScoreFunctionBuilder);

            RescorerQuery rescorerQuery = new RescorerQuery(new NativeSearchQuery(functionScoreQueryBuilder))
                    .withWindowSize(this.rescoreWindowSize);

            if (this.rescoreQueryWeight != null) {
                rescorerQuery.withQueryWeight(this.rescoreQueryWeight);
            }

            if (this.rescoreWeight != null) {
                rescorerQuery.withRescoreQueryWeight(this.rescoreWeight);
            }

            CustomNativeSearchQuery customQuery = new CustomNativeSearchQuery(nativeSearchQuery);
            customQuery.addRescorer(rescorerQuery);
            customQuery.setHighlightResultMapper(this.highlightResultMapper);
            return customQuery;
        } else {
            return nativeSearchQuery;
        }
    }

    @Override
    public String buildQueryJson(NativeSearchQuery nativeSearchQuery) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 1. 基础查询与过滤
        // 移除function_score中的重打分脚本，只保留原始查询
        QueryBuilder originalQuery = nativeSearchQuery.getQuery();
        if (originalQuery instanceof FunctionScoreQueryBuilder) {
            // 如果是function_score查询，提取其中的基础查询
            FunctionScoreQueryBuilder functionScoreQuery = (FunctionScoreQueryBuilder) originalQuery;
            sourceBuilder.query(functionScoreQuery.query());
        } else {
            // 否则使用原始查询
            sourceBuilder.query(originalQuery);
        }

        if (nativeSearchQuery.getFilter() != null) {
            sourceBuilder.postFilter(nativeSearchQuery.getFilter());
        }

        // 2. 【核心修复】找回高亮配置
        if (nativeSearchQuery.getHighlightBuilder() != null) {
            sourceBuilder.highlighter(nativeSearchQuery.getHighlightBuilder());
        } else if (nativeSearchQuery.getHighlightFields() != null) {
            // 如果是旧版或是直接设置的 fields
            HighlightBuilder hb = new HighlightBuilder();
            for (HighlightBuilder.Field field : nativeSearchQuery.getHighlightFields()) {
                hb.field(field);
            }
            sourceBuilder.highlighter(hb);
        }

        // 3. 重打分逻辑 (Rescore) - 添加独立的rescore字段
        if (rescoreWindowSize != null && rescoreScript != null) {
            boolean isInline = rescoreScript.contains("\n") || rescoreScript.contains("//") || rescoreScript.contains(";");
            Script script = new Script(
                    isInline ? ScriptType.INLINE : ScriptType.STORED,
                    isInline ? "painless" : null,
                    rescoreScript,
                    rescoreScriptParams != null ? rescoreScriptParams : new HashMap<>()
            );

            ScriptScoreQueryBuilder scriptScoreQuery = QueryBuilders.scriptScoreQuery(
                    QueryBuilders.matchAllQuery(),
                    script
            );

            QueryRescorerBuilder rescorer = new QueryRescorerBuilder(scriptScoreQuery)
                    .windowSize(rescoreWindowSize)
                    .setQueryWeight(rescoreQueryWeight != null ? rescoreQueryWeight.floatValue() : 0.0f)
                    .setRescoreQueryWeight(rescoreWeight != null ? rescoreWeight.floatValue() : 1.0f);

            sourceBuilder.addRescorer(rescorer);
        }

        // 4. 源过滤 (Source Filter)
        if (nativeSearchQuery.getSourceFilter() != null) {
            SourceFilter sourceFilter = nativeSearchQuery.getSourceFilter();
            sourceBuilder.fetchSource(sourceFilter.getIncludes(), sourceFilter.getExcludes());
        }

        // 5. 分页与排序
        Pageable pageable = nativeSearchQuery.getPageable();
        if (pageable.isPaged()) {
            sourceBuilder.from((int) pageable.getOffset());
            sourceBuilder.size(pageable.getPageSize());
        }
        if (nativeSearchQuery.getSort() != null) {
            nativeSearchQuery.getSort().forEach(order -> {
                sourceBuilder.sort(order.getProperty(),
                        order.getDirection().isAscending() ? SortOrder.ASC : SortOrder.DESC);
            });
        }

        return sourceBuilder.toString();
    }
}