package com.gushushu.yanao.usersys.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gushushu.yanao.usersys.model.BackMember;
import com.gushushu.yanao.usersys.serialize.DateSerialize;
import com.gushushu.yanao.usersys.service.impl.MemberServiceImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;

import java.util.Date;

import static com.gushushu.yanao.usersys.entity.QMember.member;
import static com.gushushu.yanao.usersys.entity.QMemberSession.memberSession;

public interface QBeans {


    public static class MemberTypeModel{
        private String type;
        private String memberId;

        @Override
        public String toString() {
            return "MemberTypeModel{" +
                    "type='" + type + '\'' +
                    ", memberId='" + memberId + '\'' +
                    '}';
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
    }
    public final static QBean<MemberTypeModel> MEMBER_TYPE = Projections.bean(
            MemberTypeModel.class,
            memberSession.member.type,
            memberSession.member.memberId
    );


    public static class MemberOpenAccountModel{
        private String openAccountStatus;
        private String memberId;

        @Override
        public String toString() {
            return "MemberOpenAccountModel{" +
                    "openAccountStatus='" + openAccountStatus + '\'' +
                    ", memberId='" + memberId + '\'' +
                    '}';
        }

        public String getOpenAccountStatus() {
            return openAccountStatus;
        }

        public void setOpenAccountStatus(String openAccountStatus) {
            this.openAccountStatus = openAccountStatus;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
    }

    public final static QBean<MemberOpenAccountModel> MEMBER_OPEN_ACCOUNT_STATUS = Projections.bean(
            MemberOpenAccountModel.class,
            member.openAccountStatus,
            member.memberId
    );


    public static class MemberForMangerModel {
        private String memberId;
        private String account;//账户 (手机号)
        @JsonSerialize(using = DateSerialize.class)
        private Date createDate;// 创建日期
        private String openAccountStatus;//开户状态
        private String type;//用户类型

        @Override
        public String toString() {
            return "MemberForMangerModel{" +
                    "memberId='" + memberId + '\'' +
                    ", account='" + account + '\'' +
                    ", createDate=" + createDate +
                    ", openAccountStatus='" + openAccountStatus + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public String getOpenAccountStatus() {
            return openAccountStatus;
        }

        public void setOpenAccountStatus(String openAccountStatus) {
            this.openAccountStatus = openAccountStatus;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public final static QBean<BackMember> MEMBER_MANAGER = Projections.bean(
            BackMember.class,
            member.memberId,
            member.type,
            member.account,//账户 (手机号)
            member.createDate,// 创建日期
            member.openAccountStatus//是否已经开户
    );





    public static class MemberSessionModel{
        private String token;
        private String memberId;
        private String account;
        private String type;
        private String openAccountStatus;
        private String innerDiscAccount;

        @Override
        public String toString() {
            return "MemberSessionModel{" +
                    "token='" + token + '\'' +
                    ", memberId='" + memberId + '\'' +
                    ", account='" + account + '\'' +
                    ", type='" + type + '\'' +
                    ", openAccountStatus='" + openAccountStatus + '\'' +
                    ", innerDiscAccount='" + innerDiscAccount + '\'' +
                    '}';
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOpenAccountStatus() {
            return openAccountStatus;
        }

        public void setOpenAccountStatus(String openAccountStatus) {
            this.openAccountStatus = openAccountStatus;
        }

        public String getInnerDiscAccount() {
            return innerDiscAccount;
        }

        public void setInnerDiscAccount(String innerDiscAccount) {
            this.innerDiscAccount = innerDiscAccount;
        }
    }
    public final static QBean<MemberSessionModel> MEMBER_SESSION_Q_BEAN = Projections.bean(
            MemberSessionModel.class,
            memberSession.token,
            memberSession.member.memberId,
            memberSession.member.account,
            memberSession.member.type,
            memberSession.member.openAccountStatus,
            memberSession.member.innerDiscAccount
    );




}
