package com.qmd.mode.borrow;

import java.util.List;

public class AwarInfo {

    private int isAward;
    private Integer myAwar;
    private List<AwarListinfo> awarlist;

    public int getIsAward() {
        return isAward;
    }

    public void setIsAward(int isAward) {
        this.isAward = isAward;
    }


    public List<AwarListinfo> getAwarlist() {
        return awarlist;
    }

    public void setAwarlist(List<AwarListinfo> awarlist) {
        this.awarlist = awarlist;
    }

    public Integer getMyAwar() {
        return myAwar;
    }

    public void setMyAwar(Integer myAwar) {
        this.myAwar = myAwar;
    }
}
