package com.qmd.mode.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 数据配置表【包含下拉列表数据.比如：民族】
 * @author xsf
 *
 */
public class Listing implements Serializable {
	private static final long serialVersionUID = 8789111452612697197L;
	
    private Integer id;//编号
    private Date createDate;
    private Date modifyDate;
    private String name;//名称
    private String sign;//Key 全部为小写
    private Integer grade;//层级
    private Integer orderList;//排序
    private String keyValue;//Key值
    private String description;//描述
    private String path;//树路径
    private Boolean isEnabled;//是否启用
    private Integer parent_id;//上级
	private String logo;//LOGO图片
	
	private List<Listing> subListing;
    
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getOrderList() {
		return orderList;
	}
	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public List<Listing> getSubListing() {
		return subListing;
	}
	public void setSubListing(List<Listing> subListing) {
		this.subListing = subListing;
	}
	
}
