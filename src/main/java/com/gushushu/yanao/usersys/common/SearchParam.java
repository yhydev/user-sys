package com.gushushu.yanao.usersys.common;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

import java.util.List;

public interface SearchParam<ResultBean> {

    List<Predicate> getPredicate();

    void setPredicate(Predicate... predicate);

    List<Path> getPath();

    void setPath(Predicate... predicate);


}
