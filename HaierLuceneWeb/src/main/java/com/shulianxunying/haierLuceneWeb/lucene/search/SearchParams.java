package com.shulianxunying.haierLuceneWeb.lucene.search;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by ChrisLee.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchParams {
    private String indexDir = "E:\\workspaces\\workspaces\\index";
    private String queryString = "计算机";

    private String sex = "all";
    private String location = "all";
    private String degree = "all";
    private String category = "all";
    private String source = "all";

    private int minExperience = -1;
    private int maxExperience = 1000;
    private int minRefresh = 1;
    private int maxRefresh = 20200101;
    private int page = 1;
    private int pageSize = 30;
    private float scoreFactor = 1 / 10f;
    private float coverageFactor = 1f;
    private float abilityFactor = 1 / 1.2f;

    public String getIndexDir() {
        return indexDir;
    }

    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(int minExperience) {
        this.minExperience = minExperience;
    }

    public int getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(int maxExperience) {
        this.maxExperience = maxExperience;
    }

    public int getMinRefresh() {
        return minRefresh;
    }

    public void setMinRefresh(int minRefresh) {
        this.minRefresh = minRefresh;
    }

    public int getMaxRefresh() {
        return maxRefresh;
    }

    public void setMaxRefresh(int maxRefresh) {
        this.maxRefresh = maxRefresh;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public float getScoreFactor() {
        return scoreFactor;
    }

    public void setScoreFactor(float scoreFactor) {
        this.scoreFactor = scoreFactor;
    }

    public float getCoverageFactor() {
        return coverageFactor;
    }

    public void setCoverageFactor(float coverageFactor) {
        this.coverageFactor = coverageFactor;
    }

    public float getAbilityFactor() {
        return abilityFactor;
    }

    public void setAbilityFactor(float abilityFactor) {
        this.abilityFactor = abilityFactor;
    }
}
