package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AccountBank extends BaseEntity {

	private static final long serialVersionUID = 4051622824813947923L;
	
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
	// 持卡人手机号
	private String phone;

	private User user;
	

	/**
	 * 账号 的取得
	 * 
	 * @return
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 账号 的设置
	 * 
	 * @param account
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 所属银行编号 的取得
	 * 
	 * @return
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * 所属银行编号 的设置
	 * 
	 * @param bankId
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * 支行名称 的取得
	 * 
	 * @return
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * 支行名称 的设置
	 * 
	 * @param branch
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * 省 的取得
	 * 
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 省 的设置
	 * 
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * 市 的取得
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 市 的设置
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 区 的取得
	 * 
	 * @return
	 */
	public String getArea() {
		return area;
	}

	/**
	 * 区 的设置
	 * 
	 * @param area
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * 添加IP 的取得
	 * 
	 * @return
	 */
	public String getAddIp() {
		return addIp;
	}

	/**
	 * 添加IP 的设置
	 * 
	 * @param addIp
	 */
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	/**
	 * 0:签约中1：签约成功 的取得
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 0:签约中1：签约成功 的设置
	 * 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 签约充值订单号 的取得
	 * 
	 * @return
	 */
	public String getTradeNo() {
		return tradeNo;
	}

	/**
	 * 签约充值订单号 的设置
	 * 
	 * @param tradeNo
	 */
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	/**
	 * 对接服务商ID（仅用于对接服务商添加提现银行账户时） 的取得
	 * 
	 * @return
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/**
	 * 对接服务商ID（仅用于对接服务商添加提现银行账户时） 的设置
	 * 
	 * @param agencyId
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userBank_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}