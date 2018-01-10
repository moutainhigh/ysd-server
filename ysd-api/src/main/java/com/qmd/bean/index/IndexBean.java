package com.qmd.bean.index;

import com.qmd.bean.BaseBean;
import com.qmd.mode.activity.Activity;

import java.util.List;

public class IndexBean extends BaseBean {

	private static final long serialVersionUID = -7683603982464447706L;

	public IndexBean() {
		setRcd("R0001");
	}

	private IndexBorrow indexBorrow;
	private List<IndexTypeItem> indexTypeItemList;
	private List<IndexImageItem> indexImageItemList;
	
	private Integer totalUserNum;
	private Integer totalTenderMoney;
	
	private Integer notReadNum;
	private Integer userFlg;
	
	private Activity activity;

	
	
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Integer getTotalTenderMoney() {
		return totalTenderMoney;
	}

	public void setTotalTenderMoney(Integer totalTenderMoney) {
		this.totalTenderMoney = totalTenderMoney;
	}

	public Integer getTotalUserNum() {
		return totalUserNum;
	}

	public void setTotalUserNum(Integer totalUserNum) {
		this.totalUserNum = totalUserNum;
	}

	public IndexBorrow getIndexBorrow() {
		return indexBorrow;
	}

	public void setIndexBorrow(IndexBorrow indexBorrow) {
		this.indexBorrow = indexBorrow;
	}

	public List<IndexTypeItem> getIndexTypeItemList() {
		return indexTypeItemList;
	}

	public void setIndexTypeItemList(List<IndexTypeItem> indexTypeItemList) {
		this.indexTypeItemList = indexTypeItemList;
	}

	public List<IndexImageItem> getIndexImageItemList() {
		return indexImageItemList;
	}

	public void setIndexImageItemList(List<IndexImageItem> indexImageItemList) {
		this.indexImageItemList = indexImageItemList;
	}

	public Integer getNotReadNum() {
		return notReadNum;
	}

	public void setNotReadNum(Integer notReadNum) {
		this.notReadNum = notReadNum;
	}

	public Integer getUserFlg() {
		return userFlg;
	}

	public void setUserFlg(Integer userFlg) {
		this.userFlg = userFlg;
	}

}
