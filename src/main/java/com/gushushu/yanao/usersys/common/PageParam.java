package com.gushushu.yanao.usersys.common;

public class PageParam {

    private Integer size = 20;
    private Integer page = 0;

    @Override
    public String toString() {
        return "PageParam{" +
                "size=" + size +
                ", page=" + page +
                '}';
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
