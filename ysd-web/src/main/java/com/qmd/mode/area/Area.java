package com.qmd.mode.area;

import java.io.Serializable;
/**
 * 实体类 - 地区
 */

public class Area implements Serializable {

	private static final long serialVersionUID = -2158109459123036967L;
	
	public static final String PATH_SEPARATOR = ",";// 路径分隔符
	private Integer id;//编号
	private String name;// 名称
	private String nid;// 拼音名称
	private Integer pid;// 上级ID
	private Integer domain;// 路径path
	private Integer order_list;// 排序
	
	
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
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getDomain() {
		return domain;
	}
	public void setDomain(Integer domain) {
		this.domain = domain;
	}
	public Integer getOrder_list() {
		return order_list;
	}
	public void setOrder_list(Integer order_list) {
		this.order_list = order_list;
	}
	
	


}