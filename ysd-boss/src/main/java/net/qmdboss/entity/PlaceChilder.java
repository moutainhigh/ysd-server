package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * 渠道活动
 * @author Administrator
 *
 */
@Entity
public class PlaceChilder extends BaseEntity {

	private static final long serialVersionUID = -3578784777073189194L;
	
	private String name;
	private Integer status;//0无效；1有效
	private String url;
	private String random;
	private String img;
	private String remark;
	private Integer viewNum;
	private Integer regNum;
	private Integer loginNum;
	
	private Place place;//渠道ID
	
	private String placeName;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

	public Integer getRegNum() {
		return regNum;
	}

	public void setRegNum(Integer regNum) {
		this.regNum = regNum;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_place_childer_place")
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}
}
