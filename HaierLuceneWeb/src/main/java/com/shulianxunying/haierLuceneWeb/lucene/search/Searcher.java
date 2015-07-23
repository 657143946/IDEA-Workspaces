package com.shulianxunying.haierLuceneWeb.lucene.search;

import com.shulianxunying.haierLuceneWeb.lucene.search.customScoreQuery.AbilityAndCoverageScoreQuery;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.queries.ChainedFilter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ChrisLee.
 * 搜索器
 */
public class Searcher {
    /**
     * 搜索库
     */
    private IndexSearcher searcher = null;

    public Searcher(String indexDir) {
        File file = new File(indexDir);
        try {
            /**
             * 直接是一个索引库
             */
            IndexReader reader = DirectoryReader.open(FSDirectory.open(file));
            searcher = new IndexSearcher(reader);
        } catch (Exception e) {
            /**
             * 多个索引库
             */
            try {
                File[] files = file.listFiles();
                List<IndexReader> listReader = new LinkedList<IndexReader>();
                for (File subFile : files) {
                    IndexReader reader = DirectoryReader.open(FSDirectory.open(subFile));
                    listReader.add(reader);
                }
                IndexReader[] readers = new IndexReader[listReader.size()];
                listReader.toArray(readers);
                searcher = new IndexSearcher(new MultiReader(readers));
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }
    }

    public IndexSearcher getSearcher() {
        return searcher;
    }

    public static void haierSearch(String indexDir,
                                   String description,  // 搜索文本
                                   String sex, String location, String degree, String category, String source,// 筛选条件
                                   int minExperience, int maxExperience,  // 工作经验
                                   int minRefresh, int maxRefresh,  // 简历更新时间
                                   int page, int pageSize,  // 分页相关
                                   float scoreFactor, float coverageFactor, float abilityFactor
    ) throws ParseException, IOException {
        /**
         * 获取搜索
         */
        Searcher searcher = new Searcher(indexDir);
        /**
         * 添加搜索
         */
        Query query = QueryMaker.makeMultiStringQuery(description, "experience", "project", "education", "training");
        Query scoreQuery = new AbilityAndCoverageScoreQuery(query, scoreFactor, coverageFactor, abilityFactor);
//        Query scoreQuery = new AbilityAndCoverageScoreQuery(query, 1/10f, 1f, 1f);
        /**
         * 添加过滤条件
         */
        List<Filter> filters = new LinkedList<>();
        boolean flag = false;
        flag = (!"all".equals(sex)) && (filters.add(FilterMaker.makeStringFilter("sex", sex)));
        flag = (!"all".equals(location)) && (filters.add(FilterMaker.makeStringFilter("location", location)));
        flag = (!"all".equals(degree)) && (filters.add(FilterMaker.makeStringFilter("degree", degree)));
        flag = (!"all".equals(category)) && (filters.add(FilterMaker.makeStringFilter("category", category)));
        flag = (!"all".equals(source)) && (filters.add(FilterMaker.makeStringFilter("data_source", source)));
        filters.add(FilterMaker.makeNumericRangeFilter("work_experience", minExperience, maxExperience));
        filters.add(FilterMaker.makeNumericRangeFilter("update_time", minRefresh, maxRefresh));
        Filter[] arryFilters = new Filter[filters.size()];
        filters.toArray(arryFilters);
        ChainedFilter chainedFilter = new ChainedFilter(arryFilters, ChainedFilter.AND);

        /**
         * 搜索
         */
        TopDocs results = searcher.getSearcher().search(scoreQuery, chainedFilter, page * pageSize);
        /**
         * 打印结果
         */
        haierPrintResult(searcher.getSearcher(), results, page, pageSize,
                "data_source", "id", "work_experience", "location", "sex");
    }

    public static void haierPrintResult(IndexSearcher searcher, TopDocs topDocs, int page, int pageSize, String... fields) throws IOException {
        /**
         * 打印总命中数
         */
        System.out.println(topDocs.totalHits);
        int start = Math.max(0, (page - 1) * pageSize);
        int end = Math.min(topDocs.totalHits, page * pageSize);
        for (int i = start; i < end; i++) {
            int docID = topDocs.scoreDocs[i].doc;
            Document doc = searcher.doc(docID);
            try {
                for (String field : fields) {
                    System.out.print(doc.get(field) + " ");
                }
            } catch (Exception e) {

            }
            System.out.print(topDocs.scoreDocs[i].score);
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        String indexDir = "index";
        String queryString = "销售";

        String sex = "女";
        String location = "all";
        String degree = "本科";
        String category = "all";
        String source = "zhilian";

        int minExperience = 1;
        int maxExperience = 10;
        int minRefresh = 1;
        int maxRefresh = 20160909;
        int page = 1;
        int pageSize = 30;
        float scoreFactor = 1 / 10f;
        float coverageFactor = 1f;
        float abilityFactor = 1 / 1.2f;

        haierSearch(indexDir,
                queryString,
                sex, location, degree, category, source,
                minExperience, maxExperience,
                minRefresh, maxRefresh,
                page, pageSize,
                scoreFactor, coverageFactor, abilityFactor);
    }

}
