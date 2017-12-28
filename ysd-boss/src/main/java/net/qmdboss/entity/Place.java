package net.qmdboss.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;
/**
 * 渠道
 * @author Administrator
 *
 */
@Entity
public class Place extends BaseEntity {

	private static final long serialVersionUID = -3578784777073189194L;
	
	
	private String name;
	private String remark;

	private Set<PlaceChilder> placeChilderSet = new HashSet<PlaceChilder>();// 文章
	
	
	private Integer size;//活动数量

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

	@Transient
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
	public Set<PlaceChilder> getPlaceChilderSet() {
		return placeChilderSet;
	}

	public void setPlaceChilderSet(Set<PlaceChilder> placeChilderSet) {
		this.placeChilderSet = placeChilderSet;
	}

}
