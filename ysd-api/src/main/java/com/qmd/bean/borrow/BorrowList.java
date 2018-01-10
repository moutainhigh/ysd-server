package com.qmd.bean.borrow;

import com.qmd.bean.BaseBean;
import com.qmd.bean.PageBean;

import java.util.List;

public class BorrowList extends BaseBean {

	private static final long serialVersionUID = -7683603982464447706L;

	public BorrowList() {
		setRcd("R0001");
	}

	private List<BorrowItem> borrowItemList;

	private PageBean pageBean;

	public List<BorrowItem> getBorrowItemList() {
		return borrowItemList;
	}

	public void setBorrowItemList(List<BorrowItem> borrowItemList) {
		this.borrowItemList = borrowItemList;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

}
