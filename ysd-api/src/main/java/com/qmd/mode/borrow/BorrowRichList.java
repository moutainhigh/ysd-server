package com.qmd.mode.borrow;

import com.qmd.bean.borrow.RichPeopleBean;

import java.io.Serializable;
import java.util.List;

public class BorrowRichList implements Serializable {
    private List<RichPeopleBean> list;

    public List<RichPeopleBean> getList() {
        return list;
    }

    public void setList(List<RichPeopleBean> list) {
        this.list = list;
    }
}
