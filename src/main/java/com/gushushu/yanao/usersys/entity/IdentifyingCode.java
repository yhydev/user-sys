package com.gushushu.yanao.usersys.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class IdentifyingCode {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String identifyingCodeId;//验证码Id
    private String code;//代码(验证码)
    private String type;//
    private String status;//
    private Date createDate;//创建日期
    private String phone;//手机号

    public String getIdentifyingCodeId() {
        return identifyingCodeId;
    }

    public void setIdentifyingCodeId(String identifyingCodeId) {
        this.identifyingCodeId = identifyingCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "IdentifyingCode{" +
                "identifyingCodeId='" + identifyingCodeId + '\'' +
                ", code=" + code +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", createDate=" + createDate +
                ", phone='" + phone + '\'' +
                '}';
    }
}
