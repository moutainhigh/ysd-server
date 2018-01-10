package com.qmd.bean;

public class BorrowBean {
	
	private Integer id;
	private String name;
	private String type;
	private String showBorrowStatus;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShowBorrowStatus() {
		return showBorrowStatus;
	}
	public void setShowBorrowStatus(String showBorrowStatus) {
		this.showBorrowStatus = showBorrowStatus;
	}
	
	
//	  bid : arrData[i].id,
//      title : arrData[i].name,
//      icon : 'http://member.ikaqu.com/style/img/mark/dtb_'+arrData[i].type+'.png',
//      describe : '<div class="sc-text-warn ">' + arrData[i].showBorrowStatus + '</div>'

}
