package com.qmd.bean.userInfo;

import com.qmd.bean.BaseBean;
import com.qmd.mode.user.User;

public class UserRealBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1044762609839146720L;

	public UserRealBean() {
		setRcd("R0001");
	}

	public UserRealBean(User user) {
		setRcd("R0001");
		this.realStatus = user.getRealStatus();
		this.realName = user.getRealName();
		this.cardId = user.getCardId();
		this.sceneStatus = user.getSceneStatus();
	}

	// 实名认证是否通过
	private Integer realStatus;
	// 会员真实姓名
	private String realName;
	// 会员身份证号码
	private String cardId;
	//认证次数
	private Integer sceneStatus;

	public Integer getRealStatus() {
		return realStatus;
	}

	public void setRealStatus(Integer realStatus) {
		this.realStatus = realStatus;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Integer getSceneStatus() {
		return sceneStatus;
	}

	public void setSceneStatus(Integer sceneStatus) {
		this.sceneStatus = sceneStatus;
	}

}
