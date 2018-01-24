package com.qmd.bean.borrow;

import java.io.Serializable;

public class RichPeopleBean implements Serializable{
    private String realName;
    private double sumAccount;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public double getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(double sumAccount) {
        this.sumAccount = sumAccount;
    }
}
