package com.qmd.mode.borrow;

import java.util.Date;

public class AwarListinfo {
    private String name;
    private String awardName;
    private Integer awardNameCode;
    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getAwardNameCode() {
        return awardNameCode;
    }

    public void setAwardNameCode(Integer awardNameCode) {
        this.awardNameCode = awardNameCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }
}
