package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 实体类 - 文章分类
 */

@Entity
public class Listing extends BaseEntity {

	private static final long serialVersionUID = -5132652107151648662L;

	public static final String PATH_SEPARATOR = ",";// 树路径分隔符

	private String name;// 名称
	private String sign;// 标识【相当于Key键】
	private String keyValue;// 页面【key对应的值】
	private String description;// 页面描述
	private String path;// 树路径
	private Integer grade;// 层级
	private Integer orderList;// 排序
    private Boolean isEnabled;//是否启用
	private String logo;//LOGO图片
    
	private Listing parent;// 上级分类
	
	private Set<Listing> children = new HashSet<Listing>();// 下级分类

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false, unique = true)
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign.toLowerCase();
	}
	
	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	
	@Column(length = 3000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(nullable = false, length = 3000)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(nullable = false)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_article_category_parent")
	@OrderBy("orderList asc")
	public Listing getParent() {
		return parent;
	}

	public void setParent(Listing parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("orderList asc")
	public Set<Listing> getChildren() {
		return children;
	}

	public void setChildren(Set<Listing> children) {
		this.children = children;
	}

	
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (grade == null || grade < 0) {
			grade = 0;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
		if(isEnabled==null){
			isEnabled=false;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (grade == null || grade < 0) {
			grade = 0;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
		if(isEnabled==null){
			isEnabled=false;
		}
	}
	
}