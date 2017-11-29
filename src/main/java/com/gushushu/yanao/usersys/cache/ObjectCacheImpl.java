package com.gushushu.yanao.usersys.cache;

import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectCacheImpl implements ObjectCache {


    @Autowired
    private JedisPool jedisPool;

    @Override
    public void del(CacheObject cacheObject) {
        Jedis jedis = jedisPool.getResource();
        jedis.del(cacheObject.getKey());
    }

    public void set(CacheObject cacheObject, int exp) {
        Jedis jedis = jedisPool.getResource();
        String key = cacheObject.getKey();
        String content = new GsonBuilder().create().toJson(cacheObject);
        jedis.setex(key,exp,content);
        jedis.close();

    }

    public Object get(CacheObject cacheObject) {
        Jedis jedis = jedisPool.getResource();
        String key = cacheObject.getKey();
        String str = jedis.get(key);
        Object ret = null;
        if(str != null){
            String content = new GsonBuilder().create().toJson(cacheObject);
            ret = new GsonBuilder().create().fromJson(str,cacheObject.getClass());

        }

        jedis.close();
        return ret;
    }

    public void set(CacheObject cacheObject) {
        Jedis jedis = jedisPool.getResource();
        String key = cacheObject.getKey();
        String content = new GsonBuilder().create().toJson(cacheObject);
        jedis.set(key,content);
        jedis.close();
    }

    public Long llen(CacheObject cacheObject) {
        Jedis jedis = jedisPool.getResource();
        Long len = jedis.llen(cacheObject.getKey());
        jedis.close();
        return len;
    }

    public void lset(Long index, CacheObject cacheObject) {
        Jedis jedis = jedisPool.getResource();
        String content = new GsonBuilder().create().toJson(cacheObject);
        jedis.lset(cacheObject.getKey(),index,content);
        jedis.close();

    }

    public void lpush(CacheObject cacheObject) {
        Jedis jedis = jedisPool.getResource();
        String content = new GsonBuilder().create().toJson(cacheObject);
        jedis.lpush(cacheObject.getKey(),content);
        jedis.close();
    }

    public void rpush(CacheObject cacheObject) {
        Jedis jedis = jedisPool.getResource();
        String content = new GsonBuilder().create().toJson(cacheObject);

        jedis.rpush(cacheObject.getKey(),content);
        jedis.close();
    }

    public List<Object> lrange(Long start, Long end, CacheObject obj) {
        Jedis jedis = jedisPool.getResource();
        List<String> strlist = jedis.lrange(obj.getKey(),start,end);
        jedis.close();

        List ret = new ArrayList();
        Class type = obj.getClass();

        for(int i=0;i<strlist.size();i++){
            String str = strlist.get(i);
            Object item = new GsonBuilder().create().fromJson(str,type);
            ret.add(item);
        }
        return ret;
    }

    public Object lindex(Long index, CacheObject object) {
        Jedis jedis = jedisPool.getResource();
        String objstr = jedis.lindex(object.getKey(),index);
        Object ret = new GsonBuilder().create().fromJson(objstr,object.getClass());
        jedis.close();
        return ret;
    }


}
