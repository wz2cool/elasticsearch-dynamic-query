package com.github.wz2cool.elasticsearch.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 高亮工具类
 *
 * @author Frank
 */
public final class HighlightUtils {

    private HighlightUtils() {
        // hide constructor
    }

    /**
     * 获取最优高亮
     *
     * @param highlights 同字段（多维度分词）
     * @return 最优高亮
     */
    public static Optional<String> getBestHighlight(String... highlights) {
        if (ArrayUtils.isEmpty(highlights)) {
            return Optional.empty();
        }
        // 命中字符数量
        long maxHitLetterCount = 0;
        // 连词数量
        long maxContinuousLetterCount = 0;
        String bestHighlight = null;
        for (String highlight : highlights) {
            if (StringUtils.isBlank(highlight)) {
                continue;
            }
            final List<String> highlightWords = listHighlightWords(highlight);
            final long hitLetterCount = highlightWords.stream().mapToLong(String::length).sum();
            final long continuousLetterCount = highlightWords.stream().mapToLong(String::length).max().orElse(0);
            if (hitLetterCount > maxHitLetterCount
                    || (hitLetterCount == maxHitLetterCount && continuousLetterCount > maxContinuousLetterCount)) {
                bestHighlight = highlight;
                maxHitLetterCount = hitLetterCount;
                maxContinuousLetterCount = continuousLetterCount;
            }
        }
        return Optional.ofNullable(bestHighlight);
    }

    /**
     * 列出高亮词
     *
     * @param highlight 高亮(默认em 包裹)
     * @return 高亮词
     */
    public static List<String> listHighlightWords(String highlight) {
        return listHighlightWords(highlight, "em");
    }

    /**
     * 列出高亮词
     *
     * @param highlight    高亮
     * @param highlightTag 高亮标签，默认应该是em
     * @return 高亮词
     */
    public static List<String> listHighlightWords(String highlight, String highlightTag) {
        if (StringUtils.isBlank(highlight)) {
            return new ArrayList<>();
        }
        String prefixHighlightTag = "<" + highlightTag + ">";
        String suffixHighlightTag = "</" + highlightTag + ">";
        String tmp = highlight;
        List<String> highlightWords = new ArrayList<>();
        while (true) {
            int prefixIndex = tmp.indexOf(prefixHighlightTag);
            if (prefixIndex < 0) {
                break;
            }
            int suffixIndex = tmp.indexOf(suffixHighlightTag);
            String highlightWord = tmp.substring(prefixIndex, suffixIndex).replaceFirst(prefixHighlightTag, "");
            highlightWords.add(highlightWord);
            tmp = tmp.substring(suffixIndex).replaceFirst(suffixHighlightTag, "");
        }
        return highlightWords;
    }
}
