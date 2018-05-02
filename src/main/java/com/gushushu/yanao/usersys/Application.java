package com.gushushu.yanao.usersys;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@SpringBootApplication
public class Application {

    Logger logger = Logger.getLogger(Application.class);

    public static void main(String arg[]){
        SpringApplication.run(Application.class,arg);
    }

  /*  @Bean
    public FilterRegistrationBean tokenFilterBean(@Autowired TokenFilter tokenFilter){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(tokenFilter);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }


    @Bean
    public JedisPool jedisPool(@Autowired RedisConfig redisConfig){

        logger.info("------initialize redisPoll Bean------");

        logger.info("param redisConfig:\t"+redisConfig);

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisConfig.getMinIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getMaxActive());

        JedisPool jedisPool = null;

        if(StringUtils.isEmpty(redisConfig.getPassword())){
            jedisPool = new JedisPool(jedisPoolConfig,redisConfig.getAddress(),
                    redisConfig.getPort());
        }else{
            jedisPool = new JedisPool(jedisPoolConfig,redisConfig.getAddress(),redisConfig.getPort(),
                    redisConfig.getTimeout(),redisConfig.getPassword());
        }


        Jedis jedis = jedisPool.getResource();
        jedis.ping();
        jedis.close();

        return jedisPool;

    }


*/


}
