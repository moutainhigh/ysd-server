package com.qmd.mode.admin;

import java.io.Serializable;

/**
 * 管理员
 * @author xsf
 *
 */
public class Admin implements Serializable {

	private static final long serialVersionUID = 8235780544753252051L;
	
	private Integer id;//编号-adminId
	private String name;//管理员名字
	private String roleName;//职位
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
