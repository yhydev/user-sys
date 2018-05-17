package com.gushushu.yanao.usersys;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.gushushu.yanao.usersys.filter.ImageCodeAuthFilter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;


@SpringBootApplication
public class Application {

    static Logger logger = Logger.getLogger(Application.class);


    @Autowired


    public static void main(String arg[]){
        ApplicationContext applicationContext = SpringApplication.run(Application.class,arg);
        logger.info(Arrays.toString(applicationContext.getBeanDefinitionNames()));






    }


    /**
     * 图片验证码参数
     * @return
     */
    @Bean
    public FilterRegistrationBean imageCodeAuth(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new ImageCodeAuthFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(@Autowired EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public DefaultKaptcha defaultKaptcha() throws IOException {

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        InputStream inputStream = Application.class.getResourceAsStream("/kaptcha.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        inputStream.close();
        return defaultKaptcha;

    }




}
