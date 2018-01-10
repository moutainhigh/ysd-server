package net.qmdboss.entity;

import org.hibernate.search.annotations.DocumentId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
/**
 * 实体类 - 地区
 */
@Entity
public class Area implements Serializable {

	private static final long serialVersionUID = -3362043954499674064L;
	public static final String PATH_SEPARATOR = ",";// 路径分隔符
	private Integer id;//编号
	private String name;// 名称
	private String nid;// 拼音名称
	private Integer pid;// 上级ID
	private String domain;// 路径path
	private Integer orderList;// 排序
	

	@DocumentId
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Integer getOrderList() {
		return orderList;
	}
	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	


}