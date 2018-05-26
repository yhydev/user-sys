package com.gushushu.yanao.usersys.model;

import java.util.List;

public class QueryData<T> {

    private List<T> results;
    private Integer page;
    private Integer size;
    private Long total;

    public QueryData(List<T> results, Integer page, Integer size, Long total) {
        this.results = results;
        this.page = page;
        this.size = size;
        this.total = total;
    }

    @Override
    public String toString() {
        return "QueryData{" +
                "results=" + results +
                ", page=" + page +
                ", size=" + size +
                ", total=" + total +
                '}';
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
