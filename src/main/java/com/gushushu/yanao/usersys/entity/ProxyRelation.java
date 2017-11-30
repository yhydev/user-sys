package com.gushushu.yanao.usersys.entity;

import javax.persistence.Entity;

/**
 *代理关系表
 */
@Entity
public class ProxyRelation {

    private String proxyRelationId; // id
    private String proxyUserId;// 代理用户id
    private String userId; //用户id
    private String createDate;//创建时间

}
