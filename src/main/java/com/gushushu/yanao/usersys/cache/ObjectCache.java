package com.gushushu.yanao.usersys.cache;

import java.util.List;

public interface ObjectCache {


    void del(CacheObject cacheObject);

    void set(CacheObject cacheObject, int exp);

    Object get(CacheObject cacheObject);

    void set(CacheObject cacheObject);


    Long llen(CacheObject cacheObject);

    void lset(Long index, CacheObject cacheObject);

    void lpush(CacheObject cacheObject);

    void rpush(CacheObject cacheObject);

    List<Object> lrange(Long begin, Long end, CacheObject obj);

    Object lindex(Long index, CacheObject object);



}
