package com.qmd.mode.borrow;

import java.util.List;

public class AwarInfo {

    private String isAward;
    private String myAwar;
    private Integer awardCode;
    private List<AwarListinfo> awarlist;

    public Integer getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(Integer awardCode) {
        this.awardCode = awardCode;
    }

    public String getMyAwar() {
        return myAwar;
    }

    public void setMyAwar(String myAwar) {
        this.myAwar = myAwar;
    }

    public String getIsAward() {
        return isAward;
    }

    public void setIsAward(String isAward) {
        this.isAward = isAward;
    }



    public List<AwarListinfo> getAwarlist() {
        return awarlist;
    }

    public void setAwarlist(List<AwarListinfo> awarlist) {
        this.awarlist = awarlist;
    }
}
