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
        if(size > 100){
            return 100;
        }
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        if(page < 0){
            return 0;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
