package com.qmd.mode.activity;

import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable {

	private static final long serialVersionUID = -103856913834937596L;
	// 编号
	private Integer id;
	// 创建时间
	private Date createDate;
	// 修改时间
	private Date modifyDate;
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
	// 内容
	private String content;
	// 状态：0未开始，1进行中，2已结束，-1下架
	private Integer status;
	// 排序
	private Integer orderList;
	private int[] array ;

	/**
	 * 编号 的取得
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 编号 的设置
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 创建时间 的取得
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 创建时间 的设置
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 修改时间 的取得
	 * 
	 * @return
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 修改时间 的设置
	 * 
	 * @param modifyDate
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

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
	 * 状态：0未开始，1进行中，2已结束，-1下架 的取得
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态：0未开始，1进行中，2已结束，-1下架 的设置
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

	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

}