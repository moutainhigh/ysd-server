package com.qmd.mode.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Hongbao  implements Serializable {

	private static final long serialVersionUID = 829047372653121759L;
	
	private Integer id;//编号
    private Date createDate;
    private Date modifyDate;
	private Integer type;//红包类型【1:注册红包；2：首投红包；3：好友实名红包；】
	private BigDecimal total;//总金额
	private Integer fiftieth;//50元红包个数
	private Integer twentieth;//20元红包个数
	private Integer tenth; //10元红包个数
	private Integer isEnabled;//是否启用【0：未启用；1：启用】
	private String remark; //发放明细说明
	private String hongbaoDetail;//红包Json【红包金额，有效期，项目期限，可用终端】
	
	public Integer getId() {
		return id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public Integer getType() {
		return type;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public Integer getFiftieth() {
		return fiftieth;
	}
	public Integer getTwentieth() {
		return twentieth;
	}
	public Integer getTenth() {
		return tenth;
	}
	public Integer getIsEnabled() {
		return isEnabled;
	}
	public String getRemark() {
		return remark;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public void setFiftieth(Integer fiftieth) {
		this.fiftieth = fiftieth;
	}
	public void setTwentieth(Integer twentieth) {
		this.twentieth = twentieth;
	}
	public void setTenth(Integer tenth) {
		this.tenth = tenth;
	}
	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHongbaoDetail() {
		return hongbaoDetail;
	}
	public void setHongbaoDetail(String hongbaoDetail) {
		this.hongbaoDetail = hongbaoDetail;
	}
}
