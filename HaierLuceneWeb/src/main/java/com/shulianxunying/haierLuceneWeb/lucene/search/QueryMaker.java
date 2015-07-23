package com.shulianxunying.haierLuceneWeb.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Created by ChrisLee.
 * 查询构造器
 */
public class QueryMaker {
    /**
     * 过滤掉非法字符
     */
    private static String queryStringFilter(String query) {
        return query.replace("/", " ").replace("\\", " ");
    }

    /**
     * 构造查询
     */
    public static Query makeMultiStringQuery(String query, QueryParser.Operator operator,
                                             Analyzer analyzer, String... fields) throws ParseException {
        BooleanQuery.setMaxClauseCount(32768);
        query = queryStringFilter(query);  // 过滤非法字符
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        parser.setDefaultOperator(operator);
        return parser.parse(query);
    }

    /**
     * 多字段查询，OR操作，只能分词
     */
    public static Query makeMultiStringQuery(String query, String... field) throws ParseException {
        return makeMultiStringQuery(query, QueryParser.Operator.OR, new IKAnalyzer(true), field);
    }


    public static Query makeStringQuery(String field, String queryStr) throws ParseException {
        queryStr = queryStringFilter(queryStr);
        QueryParser parser = new QueryParser(field, new IKAnalyzer(true));
        return parser.parse(queryStr);
    }


    public static Query makeTermQuery(String field, String termStr) {
        return new TermQuery(new Term(field, termStr));
    }

}
