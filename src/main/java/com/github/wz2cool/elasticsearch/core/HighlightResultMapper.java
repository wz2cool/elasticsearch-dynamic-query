package com.github.wz2cool.elasticsearch.core;

import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * elasticsearch 高亮结果映射
 *
 * @author Frank
 **/
public class HighlightResultMapper {

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

    public <T> T mapResult(SearchHit<T> searchHit, Class<T> clazz) {
        final T result = searchHit.getContent();
        if (CLASS_SCORE_MAP.containsKey(clazz)) {
            // 设置分数
            BiConsumer<T, Float> setScorePropertyFunc = (BiConsumer<T, Float>) CLASS_SCORE_MAP.get(clazz);
            setScorePropertyFunc.accept(result, searchHit.getScore());
        }
        final Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
        Map<String, BiConsumer<?, String>> propertyHitMap = CLASS_PROPERTY_MAP.get(clazz);
        for (Map.Entry<String, List<String>> stringListEntry : highlightFields.entrySet()) {
            String hitProperty = toHitProperty(stringListEntry.getKey());
            if (!propertyHitMap.containsKey(hitProperty)) {
                continue;
            }
            String hitText = stringListEntry.getValue().get(0);
            BiConsumer<T, String> setHitPropertyFunc = (BiConsumer<T, String>) propertyHitMap.get(hitProperty);
            setHitPropertyFunc.accept(result, hitText);
        }
        return result;
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