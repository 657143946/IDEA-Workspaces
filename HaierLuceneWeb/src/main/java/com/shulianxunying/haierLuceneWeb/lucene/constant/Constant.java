package com.shulianxunying.haierLuceneWeb.lucene.constant;

import com.shulianxunying.haierLuceneWeb.lucene.manager.IndexManager;
import com.shulianxunying.haierLuceneWeb.utils.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.util.Properties;

/**
 * Created by AbnerLee on 15-1-20.
 * 常量类
 * 加载lucene使用的常量，包括索引库的地址，使用的分词器
 */
public class Constant {
    public final static Properties LUCENE_PROPERTIES = getLuceneProperties();

    public static final String COMBINE_RESUME_INDEX_PATH = LUCENE_PROPERTIES.getProperty("combineIdnex", null);

    public static final Analyzer ANALYZER_SMART = new IKAnalyzer(true);
    public static final Analyzer ANALYZER_FALSE = new IKAnalyzer(false);
    public static final int PAGE_SIZE = 10;
    public static final int MAX_USER_TAGS = 100;

    public static final IndexManager combineResume = new IndexManager(COMBINE_RESUME_INDEX_PATH);

    static {
        assert COMBINE_RESUME_INDEX_PATH != null;
    }


    private static Properties getLuceneProperties() {
        try {
            return Reader.readProperties("/properties/lucene.properties", Reader.UTF8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
