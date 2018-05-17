package com.gushushu.yanao.usersys.model;

import java.util.Date;

public class FrontTransaction {

    private String status;
    private String type;
    private Long money;
    private Date createDate;
    private Date updateDate;
    private String answer;
    private String detailId;

  /*  public FrontTransaction(String status, String type, Long money, Date createDate, Date updateDate, String answer, String detailId) {
        this.status = status;
        this.type = type;
        this.money = money;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.answer = answer;
        this.detailId = detailId;
    }
*/
    @Override
    public String toString() {
        return "FrontTransaction{" +
                "status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", money='" + money + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", answer='" + answer + '\'' +
                ", detailId='" + detailId + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}
