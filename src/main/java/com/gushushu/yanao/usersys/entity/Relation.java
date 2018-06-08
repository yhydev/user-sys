package com.gushushu.yanao.usersys.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 *
 * @author yanyang
 * @version 1.0
 * @date 06-06 2018
 * @description 关系实体类，此类用来描述 用户与代理之间的关系
 */
@Entity
public class Relation {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String relationId;


    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;//普通用户


    @OneToOne
    @JoinColumn(name = "proxy_id")
    private Member proxyMember;//代理用户


    @Override
    public String toString() {
        return "Relation{" +
                "relationId='" + relationId + '\'' +
                ", member=" + member +
                ", proxyMember=" + proxyMember +
                '}';
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getProxyMember() {
        return proxyMember;
    }

    public void setProxyMember(Member proxyMember) {
        this.proxyMember = proxyMember;
    }


}
