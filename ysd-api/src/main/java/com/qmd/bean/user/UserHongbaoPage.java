/**
 * 
 */
package com.qmd.bean.user;

import com.qmd.bean.BaseBean;
import com.qmd.bean.PageBean;

import java.util.List;

/**
 * 用户红包，包装分页
 * @author zdl
 */
public class UserHongbaoPage extends BaseBean{

	private static final long serialVersionUID = 2028628841581161216L;
	
	public UserHongbaoPage() {
		setRcd("R0001");
	}
	
	private List<UserHongbaoView> userHongbaoViews;//红包列表
	private PageBean pageBean;//分页信息

	public List<UserHongbaoView> getUserHongbaoViews() {
		return userHongbaoViews;
	}
	public void setUserHongbaoViews(List<UserHongbaoView> userHongbaoViews) {
		this.userHongbaoViews = userHongbaoViews;
	}
	public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	
}
