package net.qmdboss.bean;

import net.qmdboss.entity.AccountBank;
import net.qmdboss.entity.User;

public class AccountBankItem {

	private Integer id;
	// 账号
	private String account;
	// 所属银行编号
	private String bankId;
	// 支行名称
	private String branch;
	// 省
	private String province;
	// 市
	private String city;
	// 区
	private String area;
	// 添加IP
	private String addIp;
	// 0:签约中1：签约成功
	private Integer status;
	// 签约充值订单号
	private String tradeNo;
	// 对接服务商ID（仅用于对接服务商添加提现银行账户时）
	private Long agencyId;

	private User user;

	private String bankName;// 银行

	public AccountBankItem() {
		super();
	}
	
	public AccountBankItem(Integer id, String account, String bankId, String branch,
			String province, String city, String area, String addIp,
			Integer status, String tradeNo, Long agencyId, User user,
			String bankName) {
		super();
		this.id = id;
		this.account = account;
		this.bankId = bankId;
		this.branch = branch;
		this.province = province;
		this.city = city;
		this.area = area;
		this.addIp = addIp;
		this.status = status;
		this.tradeNo = tradeNo;
		this.agencyId = agencyId;
		this.user = user;
		this.bankName = bankName;
	}
	
	public AccountBankItem(AccountBank bank) {
		super();
		this.id = bank.getId();
		this.account = bank.getAccount();
		this.bankId = bank.getBankId();
		this.branch = bank.getBranch();
		this.province = bank.getProvince();
		this.city = bank.getCity();
		this.area = bank.getArea();
		this.addIp = bank.getAddIp();
		this.status = bank.getStatus();
		this.tradeNo = bank.getTradeNo();
		this.agencyId = bank.getAgencyId();
		this.user = bank.getUser();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
