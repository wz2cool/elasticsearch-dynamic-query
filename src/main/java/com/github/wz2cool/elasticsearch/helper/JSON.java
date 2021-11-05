package com.github.wz2cool.elasticsearch.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wz2cool.elasticsearch.exception.JsonRuntimeException;

import java.nio.charset.Charset;
import java.util.List;

/**
 * JSON 工具对象
 *
 * @author Frank
 */
@SuppressWarnings("squid:S2301")
public final class JSON {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 反序列化的时候，忽略没有的属性
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 大小写不敏感
        MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    private JSON() {
    }

    /// region to string

    /**
     * 序列化对象编程json 字符串
     *
     * @param object 对象
     * @return json字符串
     */
    public static String toJSONString(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonRuntimeException("toJSONString error", e);
        }
    }

    /**
     * 转换json 变成对象
     *
     * @param json  json 字符串
     * @param clazz 实体
     * @param <T>   实体类
     * @return 实体类对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            String errMsg = String.format("parseObject error, json: %s; class: %s", json, clazz.getName());
            throw new JsonRuntimeException(errMsg, e);
        }
    }

    /**
     * 转换json 变成复杂泛型对象
     *
     * @param json          json 字符串
     * @param typeReference 复杂泛型类型
     * @param <T>           实体类
     * @return 实体类对象
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            String errMsg = String.format("parse complex Object error, json: %s", json);
            throw new JsonRuntimeException(errMsg, e);
        }
    }

    /**
     * 转换字节数组为复杂对象
     *
     * @param body          字节数组
     * @param charset       编码方式
     * @param typeReference 复杂泛型类型
     * @param <T>           实体类
     * @return 实体类对象
     */
    public static <T> T parseObject(byte[] body, Charset charset, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(new String(body, charset), typeReference);
        } catch (JsonProcessingException e) {
            throw new JsonRuntimeException("parse byte body error", e);
        }
    }

    /**
     * 转化json 编程数组
     *
     * @param json  json 字符串
     * @param clazz 实体
     * @param <T>   实体类
     * @return 实体类对象
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            String errMsg = String.format("parseArray error, json: %s; class: %s", json, clazz.getName());
            throw new JsonRuntimeException(errMsg, e);
        }
    }

    /// endregion
}
