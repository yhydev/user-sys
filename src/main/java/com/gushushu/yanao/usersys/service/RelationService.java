package com.gushushu.yanao.usersys.service;


import com.gushushu.yanao.usersys.common.ResponseBody;
import com.gushushu.yanao.usersys.entity.Member;
import com.gushushu.yanao.usersys.model.BackMember;
import com.gushushu.yanao.usersys.model.QueryData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author yanyang
 * @description 关系服务接口
 */
@Service
public interface RelationService {


    ResponseEntity<ResponseBody> create(CreateParam createParam);

    ResponseEntity<ResponseBody<QueryData<BackMember>>> find(String proxyMemberId);



    public static class CreateParam{

        private String memberId;
        private String proxyId;


        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getProxyId() {
            return proxyId;
        }

        public void setProxyId(String proxyId) {
            this.proxyId = proxyId;
        }
    }


}
