package com.shulianxunying.haierLuceneWeb.controller.haier;

import com.shulianxunying.haierLuceneWeb.lucene.constant.Constant;
import com.shulianxunying.haierLuceneWeb.lucene.search.FilterMaker;
import com.shulianxunying.haierLuceneWeb.lucene.search.QueryMaker;
import com.shulianxunying.haierLuceneWeb.lucene.search.customScoreQuery.AbilityAndCoverageScoreQuery;
import org.apache.lucene.document.Document;
import org.apache.lucene.queries.ChainedFilter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * Created by ChrisLee.
 */
@RestController
@RequestMapping("/xunying")
public class HaierSearchController {
    @RequestMapping("/searchCombineList")
    public Map<String, Object> haierSearchCombineList(
            /**搜索文本**/
            @RequestParam(defaultValue = "计算机,金融,财经," +
                    "工程师,销售 质量 市场 地产 研发企划 服务 客服 生产制造") String description,
            /**筛选条件**/
            @RequestParam(defaultValue = "all") String sex,
            @RequestParam(defaultValue = "all") String location,
            @RequestParam(defaultValue = "all") String degree,
            @RequestParam(defaultValue = "all") String category,
            @RequestParam(defaultValue = "all") String source,
            /**工作经验**/
            @RequestParam(defaultValue = "-1") int minExperience,
            @RequestParam(defaultValue = "100") int maxExperience,
            /**简历更新时间**/
            @RequestParam(defaultValue = "-1") int minRefresh,
            @RequestParam(defaultValue = "20500101") int maxRefresh,
            /**分页相关**/
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int pageSize,
            /**排序影响因子**/
            @RequestParam(defaultValue = "0.1") float scoreFactor,
            @RequestParam(defaultValue = "1") float coverageFactor,
            @RequestParam(defaultValue = "0.833") float abilityFactor
    ) throws IOException, ParseException {
        Map<String, Object> ret = new HashMap<>();
        /**
         * 添加搜索
         */
        Query query = QueryMaker.makeMultiStringQuery(description, "experience", "project", "education", "training");
        Query scoreQuery = new AbilityAndCoverageScoreQuery(query, scoreFactor, coverageFactor, abilityFactor);
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
        TopDocs topDocs = Constant.combineResume.getSearcher().search(scoreQuery, chainedFilter, page * pageSize);
        /**
         * 取指定页数的结果
         */
        Map<String, Float> cvIdScore = new HashMap<>();
        int start = Math.max(0, (page - 1) * pageSize);
        int end = Math.min(topDocs.totalHits, page * pageSize);
        for (int i = start; i < end; i++) {
            int docID = topDocs.scoreDocs[i].doc;
            Document doc = Constant.combineResume.getSearcher().doc(docID);
            String id = doc.get("id");
            float score = topDocs.scoreDocs[i].score;
            cvIdScore.put(id, score);
        }
        List<Result> resumes = FindMongo.findCombineInfoByIds(cvIdScore.keySet().toArray(new String[cvIdScore.size()]));
        for(Result resume: resumes){
            try{
                resume.setMatch(cvIdScore.get(resume.getUid()));
            } catch (Exception e){

            }
        }
        Collections.sort(resumes, new Comparator<Result>() {
            public int compare(Result o1, Result o2) {
                return o2.getMatch() - o1.getMatch() > 0? 1: -1;
            }
        });
        int total = topDocs.totalHits;
        double totalPage = Math.ceil(total/(float)pageSize);
        ret.put("count", total);
        ret.put("users", resumes);
        ret.put("page", page);
        ret.put("all_pages", totalPage);
        return ret;
    }
}
