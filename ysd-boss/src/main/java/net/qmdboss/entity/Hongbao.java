package net.qmdboss.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
public class Hongbao extends BaseEntity {

	private static final long serialVersionUID = -2793154391852104773L;

	private Integer type;//红包类型【1:注册红包；2：首投红包；3：好友实名红包；】
	private BigDecimal total;//总金额
	private Integer fiftieth;//50元红包个数
	private Integer twentieth;//20元红包个数
	private Integer tenth; //10元红包个数
	private Integer isEnabled;//是否启用【0：未启用；1：启用】
	private String remark; //发放明细说明
	
	private String hongbaoDetail;//红包Json【红包金额，有效期，项目期限，可用终端】

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

	@Transient
	public String getTypeString(){//1:注册红包；2：首投红包；3：好友实名红包；
		String str="";
		if(type.compareTo(1)==0){
			str ="注册红包";
		}else if(type.compareTo(2)==0){
			str ="首投红包";
		}else if(type.compareTo(3) ==0){
			str ="好友实名红包";
		}else if(type.compareTo(4) ==0){
			str ="土豪奖";
		}else if(type.compareTo(5) ==0){
			str ="截标奖";
		}
		return str;
	}

	public String getHongbaoDetail() {
		return hongbaoDetail;
	}

	public void setHongbaoDetail(String hongbaoDetail) {
		this.hongbaoDetail = hongbaoDetail;
	}
}
