package com.qmd.bean.center;

import java.io.Serializable;
import java.util.Date;

/**
 * 1.投资记录
 *
 */
public class UserTender  implements Serializable {

	 /**
	 * 
	 */
	private int tenderid;
	private static final long serialVersionUID = 3397381903560105633L;
	private String borrowName;//投资项目名称
	 private String borrowAccount;//投资项目总金额
	 private String apr;//投资项目年利率
	 private String tenderAccount;//投资金额
	 private Date createDate;//投资时间
	 private Integer borrowStatus;//项目状态
	 private String borrowStatusShow;//显示项目状态
	 private String interest;//借款利息
	 
	 
	public int getTenderid() {
		return tenderid;
	}

	public void setTenderid(int tenderid) {
		this.tenderid = tenderid;
	}

	public String getBorrowStatusShow() {
		String str="";
		if(borrowStatus.compareTo(1)==0 || borrowStatus.compareTo(5) ==0){
			str="等待复审";
		}else if (borrowStatus.compareTo(3)==0) {
			str="还款中";
		}else if (borrowStatus.compareTo(7)==0) {
			str="已还完";
		}
		
		return str;
	}
	
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public String getBorrowAccount() {
		return borrowAccount;
	}
	public void setBorrowAccount(String borrowAccount) {
		this.borrowAccount = borrowAccount;
	}
	public String getApr() {
		return apr;
	}
	public void setApr(String apr) {
		this.apr = apr;
	}
	public String getTenderAccount() {
		return tenderAccount;
	}
	public void setTenderAccount(String tenderAccount) {
		this.tenderAccount = tenderAccount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getBorrowStatus() {
		return borrowStatus;
	}
	public void setBorrowStatus(Integer borrowStatus) {
		this.borrowStatus = borrowStatus;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	 
	 
	 
	 
}
