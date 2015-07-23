package com.shulianxunying.haierLuceneWeb.lucene.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;

import java.util.Map;

/**
 * Created by AbnerLee on 15-2-3.
 * Filter 生成器
 */
public class FilterMaker {
    /**
     * 构造过滤器filter
     * 过滤哪些字段，必须匹配，不用匹配，或者不准匹配
     */
    public static Filter makeStringFilter(String[] fields, String[] values, BooleanClause.Occur[] flags) {
        if (fields == null || values == null || flags == null) {
            throw new RuntimeException("fields, values, flags must not be null");
        }
        int len = fields.length;
        if (len != values.length || len != flags.length) {
            throw new RuntimeException("length of fields, values and flags must equal");
        }
        BooleanQuery bQuery = new BooleanQuery();
        for (int i = 0; i < len; i++) {
            bQuery.add(new TermQuery(new Term(fields[i], values[i])), flags[i]);
        }
        return new QueryWrapperFilter(bQuery);
    }

    /**
     * 构造filter，筛选符合条件的查询，必须匹配指定的字段
     */
    public static Filter makeStringFilter(Map<String, String> fieldValueMap) {
        int len = fieldValueMap.size();
        String[] fields = new String[len];
        String[] values = new String[len];
        BooleanClause.Occur[] flags = new BooleanClause.Occur[len];

        int index = 0;
        for (String field : fieldValueMap.keySet()) {
            fields[index] = field;
            values[index] = fieldValueMap.get(field);
            flags[index] = BooleanClause.Occur.MUST;
            index++;
        }
        return makeStringFilter(fields, values, flags);
    }

    public static Filter makeNumericRangeFilter(String field, int min, int max){
        return NumericRangeFilter.newIntRange(field, min, max, true, true);
    }

    public static Filter makeStringFilter(String field, String queryStr) throws ParseException {
        return new QueryWrapperFilter(QueryMaker.makeStringQuery(field, queryStr));
    }

}
