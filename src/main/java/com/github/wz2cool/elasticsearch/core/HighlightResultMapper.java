package com.github.wz2cool.elasticsearch.core;

import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.helper.JSON;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * elasticsearch 高亮结果映射
 *
 * @author Frank
 **/
public class HighlightResultMapper implements SearchResultMapper {

    private static final Map<Class<?>, Map<String, BiConsumer<?, String>>> CLASS_PROPERTY_MAP = new ConcurrentHashMap<>();

    private static final Map<Class<?>, BiConsumer<?, Float>> CLASS_SCORE_MAP = new ConcurrentHashMap<>();


    public Map<String, BiConsumer<?, String>> getPropertyMapping(Class clazz) {
        return CLASS_PROPERTY_MAP.get(clazz);
    }

    /**
     * 注册分数映射
     *
     * @param clazz                类型
     * @param setScorePropertyFunc 分数属性
     * @param <T>                  泛型
     */
    public synchronized <T> void registerScoreMapping(Class<T> clazz, BiConsumer<T, Float> setScorePropertyFunc) {
        CLASS_SCORE_MAP.putIfAbsent(clazz, setScorePropertyFunc);
    }

    /**
     * 注册高亮映射
     *
     * @param clazz                    类型
     * @param getSearchPropertyFunc    搜索属性
     * @param setHighLightPropertyFunc 设置高亮属性方法
     * @param <T>                      泛型
     */
    public synchronized <T> void registerHitMapping(
            Class<T> clazz,
            GetPropertyFunction<T, String> getSearchPropertyFunc,
            BiConsumer<T, String> setHighLightPropertyFunc) {
        if (!CLASS_PROPERTY_MAP.containsKey(clazz)) {
            CLASS_PROPERTY_MAP.put(clazz, new ConcurrentHashMap<>());
        }
        Map<String, BiConsumer<?, String>> propertyMap = CLASS_PROPERTY_MAP.get(clazz);
        String propertyName = CommonsHelper.getPropertyName(getSearchPropertyFunc);
        propertyMap.putIfAbsent(propertyName, setHighLightPropertyFunc);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> clazz, Pageable pageable) {
        long totalHits = searchResponse.getHits().getTotalHits();
        List<T> list = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        if (ArrayUtils.isEmpty(hits.getHits())) {
            return new AggregatedPageImpl<>(list, pageable, totalHits);
        }
        Map<String, BiConsumer<?, String>> propertyHitMap = CLASS_PROPERTY_MAP.get(clazz);
        for (SearchHit searchHit : hits) {
            T item = JSON.parseObject(searchHit.getSourceAsString(), clazz);
            if (CLASS_SCORE_MAP.containsKey(clazz)) {
                // 设置分数
                BiConsumer<T, Float> setScorePropertyFunc = (BiConsumer<T, Float>) CLASS_SCORE_MAP.get(clazz);
                setScorePropertyFunc.accept(item, searchHit.getScore());
            }
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            for (Map.Entry<String, HighlightField> stringHighlightFieldEntry : highlightFields.entrySet()) {
                String hitProperty = toHitProperty(stringHighlightFieldEntry.getKey());
                if (!propertyHitMap.containsKey(hitProperty)) {
                    continue;
                }
                String hitText = stringHighlightFieldEntry.getValue().fragments()[0].toString();
                BiConsumer<T, String> setHitPropertyFunc = (BiConsumer<T, String>) propertyHitMap.get(hitProperty);
                setHitPropertyFunc.accept(item, hitText);
            }
            list.add(item);
        }
        return new AggregatedPageImpl<>(list, pageable, totalHits);
    }

    @Override
    public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
        return null;
    }

    private String toHitProperty(String esHitProperty) {
        if (StringUtils.isBlank(esHitProperty)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean needUpperCase = false;
        for (int i = 0; i < esHitProperty.length(); i++) {
            char currentChar = esHitProperty.charAt(i);
            if (needUpperCase) {
                currentChar = Character.toUpperCase(currentChar);
                needUpperCase = false;
            }
            if (currentChar == '.') {
                needUpperCase = true;
            } else {
                stringBuilder.append(currentChar);
            }
        }
        return stringBuilder.toString();
    }
}