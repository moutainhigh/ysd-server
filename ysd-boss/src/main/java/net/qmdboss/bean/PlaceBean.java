package net.qmdboss.bean;

import java.math.BigInteger;
import java.util.Date;

public class PlaceBean {

	protected Integer id;// ID
	protected Date createDate;// 创建日期
	protected Date modifyDate;// 修改日期
	private String name;
	private String remark;
	
	private BigInteger size;//活动数量

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}
	
	
	
}
