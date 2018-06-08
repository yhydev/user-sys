package com.gushushu.yanao.usersys.service;


import com.gushushu.yanao.usersys.common.QBeans;
import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.model.FrontMemberSession;
import com.querydsl.core.types.QBean;
import org.springframework.http.ResponseEntity;


/**
 * @author yanyang
 * @date 2018 06-07
 * @description 用户会话服务
 */
public interface MemberSessionService {

    /**
     * 保存用户的会话
     * @param memberId
     * @return
     */
    ResponseEntity<ResponseBody<FrontMemberSession>> create(String memberId);


    /**
     * 查找一个用户的会话
     * @param findOneParam 会话条件
     * @param <T> 返回的用户信息
     * @return
     */
    <T> ResponseEntity<ResponseBody<T>> findOne(FindOneParam<T> findOneParam);

    public static class FindOneParam<T>{
        private QBean<T> select;
        private String eqToken;

        public FindOneParam(QBean<T> qbean){
            this.setSelect(qbean);
        }

        @Override
        public String toString() {
            return "FindOneParam{" +
                    "select=" + select +
                    ", eqToken='" + eqToken + '\'' +
                    '}';
        }

        public QBean<T> getSelect() {
            return select;
        }

        public void setSelect(QBean<T> select) {
            this.select = select;
        }

        public String getEqToken() {
            return eqToken;
        }

        public void setEqToken(String eqToken) {
            this.eqToken = eqToken;
        }
    };



}
