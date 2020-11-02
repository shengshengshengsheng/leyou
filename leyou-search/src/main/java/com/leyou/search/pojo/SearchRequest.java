package com.leyou.search.pojo;

import java.util.Map;

/**
 * @author xuqiangsheng
 */
public class SearchRequest {
    private String key;// 搜索条件

    private Integer page;// 当前页

    //排序字段
    private String sortBy;

    //是否降序
    private Boolean descending;

    //过滤条件
    private Map<String,String> filter;

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

    private static final Integer DEFAULT_SIZE = 20;// 每页大小，不从页面接收，而是固定大小
    private static final Integer DEFAULT_PAGE = 1;// 默认页

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public static Integer getDefaultSize() {
        return DEFAULT_SIZE;
    }

    public static Integer getDefaultPage() {
        return DEFAULT_PAGE;
    }
}