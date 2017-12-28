package net.qmdboss.entity;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import java.util.Date;

@Indexed
@Entity
public class Activity extends BaseEntity {

	private static final long serialVersionUID = 8063066846517783756L;
	
	// 标题
	private String title;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 网页上的图片
	private String imgWeb;
	// app的图片
	private String imgApp;
	// 移动端内容
	private String content;
	// PC内容
	private String pcContent;
	// 状态0未开始，1进行中，2已结束，-1下架
	private Integer status;
	// 排序
	private Integer orderList;

	/**
	 * 标题 的取得
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 标题 的设置
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 开始时间 的取得
	 * 
	 * @return
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 开始时间 的设置
	 * 
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 结束时间 的取得
	 * 
	 * @return
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 结束时间 的设置
	 * 
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 网页上的图片 的取得
	 * 
	 * @return
	 */
	public String getImgWeb() {
		return imgWeb;
	}

	/**
	 * 网页上的图片 的设置
	 * 
	 * @param imgWeb
	 */
	public void setImgWeb(String imgWeb) {
		this.imgWeb = imgWeb;
	}

	/**
	 * app的图片 的取得
	 * 
	 * @return
	 */
	public String getImgApp() {
		return imgApp;
	}

	/**
	 * app的图片 的设置
	 * 
	 * @param imgApp
	 */
	public void setImgApp(String imgApp) {
		this.imgApp = imgApp;
	}

	/**
	 * 内容 的取得
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 内容 的设置
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 状态 的取得
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态 的设置
	 * 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 排序 的取得
	 * 
	 * @return
	 */
	public Integer getOrderList() {
		return orderList;
	}

	/**
	 * 排序 的设置
	 * 
	 * @param orderList
	 */
	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	public String getPcContent() {
		return pcContent;
	}

	public void setPcContent(String pcContent) {
		this.pcContent = pcContent;
	}

}