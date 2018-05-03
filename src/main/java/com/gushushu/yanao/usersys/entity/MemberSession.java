package com.gushushu.yanao.usersys.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MemberSession {


    @Id
    @GeneratedValue(generator = "guuid")
    @GenericGenerator(name = "guuid", strategy = "uuid")
    @Column(length = 32,columnDefinition = "char(32)")
    private String sessionId;

    @OneToOne
    @JoinColumn(name = "memberId",columnDefinition = "char(32)")
    private Member member;

    @Column(length = 32,columnDefinition = "char(32)")
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
