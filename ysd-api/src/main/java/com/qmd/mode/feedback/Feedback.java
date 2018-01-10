package com.qmd.mode.feedback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmd.util.OpinionImg;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Feedback  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date createDate;// 创建时间
	private Date modifyDate;// 修改时间
	private Integer userId;
	private String img;
	private String content;
	private Integer status;
	private String contact;//联系方式：手机号或邮箱
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
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
	
	public List<OpinionImg> getImgList(){
		List<OpinionImg> imgList = new ArrayList<OpinionImg>();
		if(StringUtils.isNotEmpty( img)){
			imgList = (List<OpinionImg>) new Gson().fromJson(img, new TypeToken<List<OpinionImg>>(){}.getType());
		}
		return imgList;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}


}
