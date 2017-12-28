package com.qmd.mode.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserAwardDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7120909338544644233L;

	// 系统ID
	private Integer id;
	// 用户ID
	private Integer userId;
	// 类型（同资金记录）
	private String type;
	// 操作金额
	private BigDecimal money;
	// 收支
	private Integer signFlg;
	// 奖励账户
	private BigDecimal awardMoney;
	// 备注
	private String remark;
	// 资金记录ID
	private Integer userAccountDetailId;
	// 相关id
	private Integer relateTo;
	// 相关项目
	private String relateKey;
	// 创建时间
	private Date createDate;
	// 修改时间
	private Date modifyDate;
	// 后备字段
	private String reserve1;
	
	private String typeName;

	private String username;
	/**
	 * 系统ID 的取得
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 系统ID 的设置
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 用户ID 的取得
	 * 
	 * @return
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 用户ID 的设置
	 * 
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 类型（同资金记录） 的取得
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 类型（同资金记录） 的设置
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 操作金额 的取得
	 * 
	 * @return
	 */
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * 操作金额 的设置
	 * 
	 * @param money
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	/**
	 * 奖励账户 的取得
	 * 
	 * @return
	 */
	public BigDecimal getAwardMoney() {
		return awardMoney;
	}

	/**
	 * 奖励账户 的设置
	 * 
	 * @param awardMoney
	 */
	public void setAwardMoney(BigDecimal awardMoney) {
		this.awardMoney = awardMoney;
	}

	/**
	 * 备注 的取得
	 * 
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注 的设置
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 资金记录ID 的取得
	 * 
	 * @return
	 */
	public Integer getUserAccountDetailId() {
		return userAccountDetailId;
	}

	/**
	 * 资金记录ID 的设置
	 * 
	 * @param userAccountDetailId
	 */
	public void setUserAccountDetailId(Integer userAccountDetailId) {
		this.userAccountDetailId = userAccountDetailId;
	}

	/**
	 * 相关id 的取得
	 * 
	 * @return
	 */
	public Integer getRelateTo() {
		return relateTo;
	}

	/**
	 * 相关id 的设置
	 * 
	 * @param relateTo
	 */
	public void setRelateTo(Integer relateTo) {
		this.relateTo = relateTo;
	}

	/**
	 * 相关项目 的取得
	 * 
	 * @return
	 */
	public String getRelateKey() {
		return relateKey;
	}

	/**
	 * 相关项目 的设置
	 * 
	 * @param relateKey
	 */
	public void setRelateKey(String relateKey) {
		this.relateKey = relateKey;
	}

	/**
	 * 创建时间 的取得
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 创建时间 的设置
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 修改时间 的取得
	 * 
	 * @return
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 修改时间 的设置
	 * 
	 * @param modifyDate
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * 后备字段 的取得
	 * 
	 * @return
	 */
	public String getReserve1() {
		return reserve1;
	}

	/**
	 * 后备字段 的设置
	 * 
	 * @param reserve1
	 */
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public Integer getSignFlg() {
		return signFlg;
	}

	public void setSignFlg(Integer signFlg) {
		this.signFlg = signFlg;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}