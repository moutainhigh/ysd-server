package com.qmd.bean.user;

import com.qmd.bean.BaseBean;

import java.util.List;

public class BorrowListingBean extends BaseBean  {

	private static final long serialVersionUID = -1968165661814942863L;

	public BorrowListingBean() {
		setRcd("R0001");
	}

	
	private List<BorrowListingItem> borlistingList;

	public List<BorrowListingItem> getBorlistingList() {
		return borlistingList;
	}


	public void setBorlistingList(List<BorrowListingItem> borlistingList) {
		this.borlistingList = borlistingList;
	}
	

	
}
