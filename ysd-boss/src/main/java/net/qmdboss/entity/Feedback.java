package net.qmdboss.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Feedback extends BaseEntity {

	private static final long serialVersionUID = 4051622824813947923L;
	
	// 图片
	private String img;
	// 内容
	private String content;
	// 0:正常1：已读
	private Integer status;


	private User user;
	


	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_userBank_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}