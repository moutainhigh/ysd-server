package com.qmd.bean.user;

import com.qmd.bean.BaseBean;
import com.qmd.bean.PageBean;

import java.util.List;

public class AwawdCashBean extends BaseBean  {

	private static final long serialVersionUID = -1968165661814942863L;

	public AwawdCashBean() {
		setRcd("R0001");
	}

	private String sumCashMoney;//累计现金奖励
	private String sumFriendsTzMoney;//累计好友投资奖励
	private String sumTzMoney;//累计投资奖励
	
	private List<AwardCashItem> cashList;
	
	private PageBean pageBean;

	public String getSumCashMoney() {
		return sumCashMoney;
	}

	public void setSumCashMoney(String sumCashMoney) {
		this.sumCashMoney = sumCashMoney;
	}

	public String getSumFriendsTzMoney() {
		return sumFriendsTzMoney;
	}

	public void setSumFriendsTzMoney(String sumFriendsTzMoney) {
		this.sumFriendsTzMoney = sumFriendsTzMoney;
	}

	public String getSumTzMoney() {
		return sumTzMoney;
	}

	public void setSumTzMoney(String sumTzMoney) {
		this.sumTzMoney = sumTzMoney;
	}

	public List<AwardCashItem> getCashList() {
		return cashList;
	}

	public void setCashList(List<AwardCashItem> cashList) {
		this.cashList = cashList;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	
	
	
	
	
	
}
