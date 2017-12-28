package com.qmd.bean.userInfo;

import com.qmd.bean.BaseBean;
import com.qmd.mode.user.User;
import org.apache.commons.lang3.StringUtils;

public class UserSafePwdBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1044762609839146720L;

	public UserSafePwdBean() {
		setRcd("R0001");
	}

	public UserSafePwdBean(User user) {
		setRcd("R0001");
		if (StringUtils.isEmpty(user.getPayPassword())) {
			this.safePwdStatus = 0;
		} else {
			this.safePwdStatus = 1;
		}
	}

	// 安全密码是否设置
	private Integer safePwdStatus;

	public Integer getSafePwdStatus() {
		return safePwdStatus;
	}

	public void setSafePwdStatus(Integer safePwdStatus) {
		this.safePwdStatus = safePwdStatus;
	}

}
